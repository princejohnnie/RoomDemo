package com.yungjohn.roomdemo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.yungjohn.roomdemo.Db.Note;
import com.yungjohn.roomdemo.Db.NoteDao;
import com.yungjohn.roomdemo.Db.NoteDatabase;

import java.util.List;

public class NoteRepository {
    private NoteDao mNoteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        mNoteDao = database.noteDao();
        allNotes = mNoteDao.getAllNotes();
    }

    public void insert(Note note) {
        new InsertNoteAsyncTask(mNoteDao).execute(note);
    }

    public void update (Note note) {
        new UpdateNoteAsyncTask(mNoteDao).execute(note);
    }

    public void delete (Note note) {
        new DeleteNoteAsyncTask(mNoteDao).execute(note);
    }

    public void deleteAllNotes () {
        new DeleteAllNotesAsyncTask(mNoteDao);
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao mNoteDao;

        private InsertNoteAsyncTask(NoteDao noteDao) {
            mNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao mNoteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao) {
            mNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao mNoteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao) {
            mNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao mNoteDao;

        private DeleteAllNotesAsyncTask(NoteDao noteDao) {
            mNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mNoteDao.deleteAllNotes();
            return null;
        }
    }


}
