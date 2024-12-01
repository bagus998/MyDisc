import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class App {

    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Song> playlist;

    public App() {
        playlist = new ArrayList<>();
        playlist.add(new Song("505", "Arctic Monkeys", 253, "images/505.jpg"));
        playlist.add(new Song("Washing Machine Heart", "Mitski", 128, "images/washing_machine_heart.jpeg"));
        playlist.add(new Song("Before", "Niki", 234, "images/before.jpg"));
        playlist.add(new Song("Pluto Projector", "Rex Orange Country", 267, "images/pluto_projector.jpeg"));
        playlist.add(new Song("Satu Bulan", "Bernadya", 200, "images/satu_bulan.jpg"));

        frame = new JFrame("MyDisc Playlist");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        tableModel = new DefaultTableModel(new String[]{"Album", "Title", "Artist", "Duration"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return ImageIcon.class;
                return String.class;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(100);
        JScrollPane scrollPane = new JScrollPane(table);


        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        JPanel buttonPanel = new JPanel();
        JButton sortTitleButton = new JButton("Sort by Title");
        JButton sortArtistButton = new JButton("Sort by Artist");
        JButton sortDurationButton = new JButton("Sort by Duration");

        // Button actions
        sortTitleButton.addActionListener(e -> {
            sortTitle();
            refreshTable();
        });

        sortArtistButton.addActionListener(e -> {
            sortArtist();
            refreshTable();
        });

        sortDurationButton.addActionListener(e -> {
            sortDuration(0, playlist.size() - 1);
            refreshTable();
        });

        // Add buttons to panel
        buttonPanel.add(sortTitleButton);
        buttonPanel.add(sortArtistButton);
        buttonPanel.add(sortDurationButton);

        // Layout
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Populate table with initial data
        refreshTable();

        frame.setVisible(true);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Song song : playlist) {
            ImageIcon albumIcon = new ImageIcon(new ImageIcon(song.albumImagePath)
                    .getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
            tableModel.addRow(new Object[]{
                    albumIcon, song.title, song.artist, song.getFormattedDuration()
            });
        }
    }

    // Sort Title (Bubble Sort)
    private void sortTitle() {
        int n = playlist.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (playlist.get(j).title.compareToIgnoreCase(playlist.get(j + 1).title) > 0) {
                    Song temp = playlist.get(j);
                    playlist.set(j, playlist.get(j + 1));
                    playlist.set(j + 1, temp);
                }
            }
        }
    }

    // Sort Artist (Selection Sort)
    private void sortArtist() {
        int n = playlist.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (playlist.get(j).artist.compareToIgnoreCase(playlist.get(minIndex).artist) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Song temp = playlist.get(i);
                playlist.set(i, playlist.get(minIndex));
                playlist.set(minIndex, temp);
            }
        }
    }

    // Sort Duration (Quick Sort)
    private void sortDuration(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
            sortDuration(low, pi - 1);
            sortDuration(pi + 1, high);
        }
    }

    private int partition(int low, int high) {
        Song pivot = playlist.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (playlist.get(j).duration < pivot.duration) {
                i++;
                Song temp = playlist.get(i);
                playlist.set(i, playlist.get(j));
                playlist.set(j, temp);
            }
        }
        Song temp = playlist.get(i + 1);
        playlist.set(i + 1, playlist.get(high));
        playlist.set(high, temp);
        return i + 1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }
}