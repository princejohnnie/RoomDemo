package com.yungjohn.roomdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.Objects;

public class AddEditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.yungjohn.roomdemo.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.yungjohn.roomdemo.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.yungjohn.roomdemo.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.yungjohn.roomdemo.EXTRA_PRIORITY";
    private EditText mEditTextTitle;
    private EditText mEditTextDescription;
    private NumberPicker mNumberPickerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        mEditTextTitle = findViewById(R.id.et_title);
        mEditTextDescription = findViewById(R.id.et_description);

        mNumberPickerPriority = findViewById(R.id.num_picker_priority);
        mNumberPickerPriority.setMaxValue(10);
        mNumberPickerPriority.setMinValue(1);

        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            mEditTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            mEditTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            mNumberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        } else {
            setTitle("Add Note");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_note_menu_item) {
            saveNote();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNote() {
        String title = mEditTextTitle.getText().toString();
        String description = mEditTextDescription.getText().toString();
        int priority = mNumberPickerPriority.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Cannot create empty note", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }
}