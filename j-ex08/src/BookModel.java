import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class BookConfig {
    public String name;
    public String author_name;
    public String publisher;
    public String ISBN;
}

public class BookModel {


    private ArrayList<BookConfig> book_list;
    private ArrayList searched_list[];
    private BookConfig target;
    private int space_count = 0;
    private int space_num = 0;

    public void makeBookList(String file_name) {
        book_list = new ArrayList<>();
        try {
            File f = new File(file_name);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                String[] buff = line.split(",", 0);
                BookConfig book = new BookConfig();
                book.name = buff[0];
                book.author_name = buff[1];
                book.publisher = buff[2];
                book.ISBN = buff[3];
                book_list.add(book);
            }
            br.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private int compareBook(BookConfig list) {
        int flag = 0;
        if (list.name.contains(target.name) && !target.name.equals("")) {
            flag += 1;
        }
        if (list.author_name.contains(target.author_name) && !target.author_name.equals("")) {
            flag += 2;
        }
        if (list.publisher.contains(target.publisher) && !target.publisher.equals("")) {
            flag += 7;
        }
        if (list.ISBN.contains(target.ISBN) && !target.ISBN.equals("")) {
            flag += 11;
        }
        return flag;
    }

    public void setTarget(String name, String author_name, String publisher, String ISBN) {
        target = new BookConfig();
        target.name = name;
        target.author_name = author_name;
        target.publisher = publisher;
        target.ISBN = ISBN;
        if (name.equals("")) {
            space_count++;
        }
        if (author_name.equals("")) {
            space_count++;
        }
        if (publisher.equals("")) {
            space_count++;
        }
        if (ISBN.equals("")) {
            space_count++;
        }
    }

    public void search() {
        searched_list = new ArrayList[5];
        for (int i = 0; i < searched_list.length; i++) {
            searched_list[i] = new ArrayList();
        }
        for (int i = 0; i < book_list.size(); i++) {
            int flag = compareBook(book_list.get(i));

            if (flag > 0) {
                if (space_count == 3) {
                    if (flag == 1) {
                        searched_list[3].add(book_list.get(i));
                    } else if (flag == 2) {
                        searched_list[3].add(book_list.get(i));
                    } else if (flag == 7) {
                        searched_list[3].add(book_list.get(i));
                    } else if (flag == 11) {
                        searched_list[3].add(book_list.get(i));
                    }
                } else if (space_count == 2) {
                    if (flag == 3) {
                        searched_list[2].add(book_list.get(i));
                    } else if (flag == 8) {
                        searched_list[2].add(book_list.get(i));
                    } else if (flag == 12) {
                        searched_list[2].add(book_list.get(i));
                    } else if (flag == 9) {
                        searched_list[2].add(book_list.get(i));
                    } else if (flag == 13) {
                        searched_list[2].add(book_list.get(i));
                    } else if (flag == 18) {
                        searched_list[2].add(book_list.get(i));
                    }
                } else if (space_count == 1) {
                    if (flag == 10) {
                        searched_list[1].add(book_list.get(i));
                    } else if (flag == 20) {
                        searched_list[1].add(book_list.get(i));
                    } else if (flag == 19) {
                        searched_list[1].add(book_list.get(i));
                    } else if (flag == 14) {
                        searched_list[1].add(book_list.get(i));
                    }
                } else if (space_count == 0) {
                    if (flag == 21) {
                        searched_list[0].add(book_list.get(i));
                    }
                }
            } else if (flag == 0 && space_count == 4) {
                searched_list[4].add(book_list.get(i));
            }
        }
    }

    public ArrayList<BookConfig> getSearchedBookList() {
        space_num = space_count;
        space_count = 0;
        return searched_list[space_num];
    }
}
