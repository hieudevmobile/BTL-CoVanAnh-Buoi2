package com.example.app_buoi2;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_buoi2.auth.AuthPreferences;
import com.example.app_buoi2.data.AppDatabase;
import com.example.app_buoi2.data.entity.Ticket;
import com.example.app_buoi2.data.model.ShowtimeRow;
import com.example.app_buoi2.ui.ToolbarHelper;
import com.example.app_buoi2.util.DateFormats;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.color.MaterialColors;

import android.database.sqlite.SQLiteConstraintException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BookTicketActivity extends AppCompatActivity {

    public static final String EXTRA_SHOWTIME_ID = "showtime_id";

    private static final String ROWS = "ABCDEF";

    private long showtimeId = -1;
    private TableLayout seatTable;
    private TextView textMovie;
    private TextView textTheater;
    private TextView textTime;
    private MaterialButton buttonConfirm;
    /** Ghế trống đang chọn (có thể nhiều ghế) */
    private final Set<String> selectedSeats = new LinkedHashSet<>();

    private final Map<String, MaterialButton> seatButtons = new HashMap<>();

    private final ActivityResultLauncher<Intent> loginLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK) {
                            confirmBooking();
                        } else {
                            Toast.makeText(this, R.string.book_need_login, Toast.LENGTH_SHORT).show();
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_ticket);
        View root = findViewById(R.id.bookScreenRoot);
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.book_title);
        ToolbarHelper.setupBack(toolbar, this);

        showtimeId = getIntent().getLongExtra(EXTRA_SHOWTIME_ID, -1L);
        if (showtimeId <= 0) {
            finish();
            return;
        }

        textMovie = findViewById(R.id.textMovie);
        textTheater = findViewById(R.id.textTheater);
        textTime = findViewById(R.id.textTime);
        seatTable = findViewById(R.id.seatTable);
        buttonConfirm = findViewById(R.id.buttonConfirm);

        buttonConfirm.setOnClickListener(v -> confirmBooking());
        loadShowtime();
    }

    private void loadShowtime() {
        AppDatabase.execute(
                () -> {
                    ShowtimeRow row =
                            AppDatabase.getInstance(this).showtimeDao().getRowById(showtimeId);
                    runOnUiThread(
                            () -> {
                                if (row == null) {
                                    Toast.makeText(this, R.string.showtime_missing, Toast.LENGTH_SHORT)
                                            .show();
                                    finish();
                                    return;
                                }
                                textMovie.setText(row.movieTitle);
                                textTheater.setText(row.theaterName);
                                textTime.setText(DateFormats.formatShow(row.startTimeMillis));
                                loadSeatsAndBuildGrid();
                            });
                });
    }

    private void loadSeatsAndBuildGrid() {
        AppDatabase.execute(
                () -> {
                    List<String> taken =
                            AppDatabase.getInstance(this).ticketDao().getSeatsForShowtime(showtimeId);
                    Set<String> takenSet = new HashSet<>(taken);
                    runOnUiThread(() -> buildSeatGrid(takenSet));
                });
    }

    private void buildSeatGrid(Set<String> taken) {
        seatTable.removeAllViews();
        seatButtons.clear();
        selectedSeats.clear();

        for (int r = 0; r < ROWS.length(); r++) {
            char rowChar = ROWS.charAt(r);
            TableRow row = new TableRow(this);
            for (int c = 1; c <= 8; c++) {
                String code = rowChar + String.valueOf(c);
                MaterialButton btn =
                        new MaterialButton(
                                this, null, com.google.android.material.R.attr.materialButtonOutlinedStyle);
                int cellPx = getResources().getDimensionPixelSize(R.dimen.seat_cell_size);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(0, cellPx, 1f);
                int p = (int) (4 * getResources().getDisplayMetrics().density);
                lp.setMargins(p, p, p, p);
                btn.setLayoutParams(lp);
                applySeatCellSizing(btn, cellPx);

                if (taken.contains(code)) {
                    styleTakenSeat(btn, code);
                } else {
                    btn.setText(code);
                    btn.setContentDescription(getString(R.string.ticket_seat_format, code));
                    btn.setCheckable(true);
                    btn.setOnClickListener(v -> toggleSeat(code, btn));
                }
                seatButtons.put(code, btn);
                row.addView(btn);
            }
            seatTable.addView(row);
        }
        refreshConfirmButton();
    }

    /**
     * Cùng chiều cao/chữ cho mọi ô — MaterialButton mặc định dễ phình theo nội dung.
     */
    private void applySeatCellSizing(MaterialButton btn, int cellPx) {
        btn.setMinWidth(0);
        btn.setMinimumWidth(0);
        btn.setMinHeight(cellPx);
        btn.setMinimumHeight(cellPx);
        btn.setMaxHeight(cellPx);
        btn.setInsetTop(0);
        btn.setInsetBottom(0);
        btn.setTextSize(
                TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.seat_text_sp));
        btn.setMaxLines(1);
        btn.setPadding(0, 0, 0, 0);
    }

    /** Ghế đã đặt: chỉ dấu ✕ bên trong, cùng kích thước ô với ghế trống */
    private void styleTakenSeat(MaterialButton btn, String code) {
        btn.setText(R.string.seat_taken_mark);
        btn.setContentDescription(getString(R.string.seat_a11y_taken, code));
        btn.setCheckable(false);
        btn.setClickable(false);
        btn.setFocusable(false);
        btn.setEnabled(true);
        int bg =
                MaterialColors.getColor(
                        btn, com.google.android.material.R.attr.colorSurfaceVariant);
        int fg =
                MaterialColors.getColor(
                        btn, com.google.android.material.R.attr.colorOnSurfaceVariant);
        btn.setBackgroundTintList(ColorStateList.valueOf(bg));
        btn.setTextColor(fg);
        btn.setRippleColor(ColorStateList.valueOf(Color.TRANSPARENT));
    }

    private void toggleSeat(String code, MaterialButton btn) {
        if (selectedSeats.contains(code)) {
            selectedSeats.remove(code);
            btn.setChecked(false);
            btn.setText(code);
        } else {
            selectedSeats.add(code);
            btn.setChecked(true);
            btn.setText(code);
        }
        refreshConfirmButton();
    }

    private void refreshConfirmButton() {
        if (selectedSeats.isEmpty()) {
            buttonConfirm.setText(R.string.book_confirm);
            buttonConfirm.setEnabled(false);
        } else {
            buttonConfirm.setText(getString(R.string.book_confirm_count, selectedSeats.size()));
            buttonConfirm.setEnabled(true);
        }
    }

    /**
     * Bấm Xác nhận: chưa chọn ghế → nhắc; chưa đăng nhập → mở Login; đã đăng nhập → tạo vé.
     * Sau khi login thành công, {@link #loginLauncher} gọi lại method này.
     */
    private void confirmBooking() {
        if (selectedSeats.isEmpty()) {
            Toast.makeText(this, R.string.book_pick_seat, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!AuthPreferences.isLoggedIn(this)) {
            loginLauncher.launch(new Intent(this, LoginActivity.class));
            return;
        }

        long userId = AuthPreferences.getUserId(this);
        final List<String> seatsToBook = new ArrayList<>(selectedSeats);

        AppDatabase.execute(
                () -> {
                    try {
                        AppDatabase db = AppDatabase.getInstance(this);
                        db.runInTransaction(
                                () -> {
                                    for (String seat : seatsToBook) {
                                        Ticket t = new Ticket();
                                        t.showtimeId = showtimeId;
                                        t.userId = userId;
                                        t.seat = seat;
                                        db.ticketDao().insert(t);
                                    }
                                });
                        runOnUiThread(
                                () -> {
                                    Toast.makeText(
                                                    this,
                                                    getString(
                                                            R.string.book_success_multi,
                                                            seatsToBook.size()),
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                    Intent i = new Intent(this, MyTicketsActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    finish();
                                });
                    } catch (SQLiteConstraintException ex) {
                        runOnUiThread(
                                () ->
                                        Toast.makeText(
                                                        this,
                                                        R.string.book_seat_taken,
                                                        Toast.LENGTH_SHORT)
                                                .show());
                    }
                });
    }
}
