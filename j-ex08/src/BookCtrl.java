import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class BookCtrl {

    private BookModel model;

    public BookCtrl() {
        model = new BookModel();
    }

    public void makeBookList(String file_name) {
        model.makeBookList(file_name);
    }

    public void search(String name, String author_name, String publisher, String ISBN) {
        model.setTarget(name, author_name, publisher, ISBN);
        model.search();
    }

    public ArrayList<BookConfig> getSearchedBookList() {
        return model.getSearchedBookList();
    }
}