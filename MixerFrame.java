import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.Random;

public class MixerFrame {
    // ===== Palet warna tema gelap =====
    private static final Color BG      = new Color(0x1E1E2E); // latar utama
    private static final Color CARD    = new Color(0x2A2A3C); // latar kartu slider
    private static final Color GARIS   = new Color(0x45475A); // garis pembatas
    private static final Color TEKS    = new Color(0xCDD6F4); // teks terang
    private static final Color MERAH   = new Color(0xF38BA8);
    private static final Color HIJAU   = new Color(0xA6E3A1);
    private static final Color BIRU    = new Color(0x89B4FA);
    private static final Color UNGU    = new Color(0xCBA6F7);
    private static final Color KUNING  = new Color(0xF9E2AF);

    private JFrame frame;
    private JPanel previewPanel, sliderPanel, buttonPanel;
    private JSlider sliderR, sliderG, sliderB;
    private JLabel lblHex, lblInfo, lblR, lblG, lblB;
    private JButton btnRandom, btnReset, btnSalin;
    private Random rnd = new Random();

    public MixerFrame() {
        // Look and feel bawaan Java supaya warna tombol bisa kita atur sendiri
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {
            // kalau gagal, pakai tampilan bawaan saja
        }

        frame = new JFrame("Color Mixer - Tugas PBO");

        // --- Panel preview (atas) ---
        previewPanel = new JPanel(new BorderLayout());
        previewPanel.setPreferredSize(new Dimension(380, 160));
        previewPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        previewPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblHex = new JLabel("#000000", JLabel.CENTER);
        lblHex.setFont(new Font("Monospaced", Font.BOLD, 42));
        lblInfo = new JLabel("RGB(0, 0, 0)", JLabel.CENTER);
        lblInfo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        previewPanel.add(lblHex, BorderLayout.CENTER);
        previewPanel.add(lblInfo, BorderLayout.SOUTH);

        // --- Slider + label nilai (tengah) ---
        sliderR = buatSlider();
        sliderG = buatSlider();
        sliderB = buatSlider();
        lblR = buatLabelNilai(MERAH);
        lblG = buatLabelNilai(HIJAU);
        lblB = buatLabelNilai(BIRU);
        sliderPanel = new JPanel(new GridLayout(3, 3, 12, 14));
        sliderPanel.setBackground(CARD);
        TitledBorder judul = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(GARIS, 1, true),
                " Atur komposisi RGB ",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 13), TEKS);
        sliderPanel.setBorder(BorderFactory.createCompoundBorder(
                judul, BorderFactory.createEmptyBorder(12, 14, 12, 14)));

        // --- Tombol (bawah) ---
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 6));
        buttonPanel.setBackground(BG);
        btnRandom = buatTombol("Acak Warna", UNGU);
        btnReset = buatTombol("Reset", KUNING);
        btnSalin = buatTombol("Salin Hex", HIJAU);
        btnRandom.setActionCommand("acak");
        btnReset.setActionCommand("reset");
        btnSalin.setActionCommand("salin");

        updateWarna(); // tampilkan warna awal
    }

    // ===== Method pembantu untuk membuat komponen yang bergaya =====
    private JSlider buatSlider() {
        JSlider s = new JSlider(0, 255, 0);
        s.setBackground(CARD);
        s.setOpaque(true);
        s.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return s;
    }

    private JLabel buatLabelNilai(Color warna) {
        JLabel l = new JLabel("0", JLabel.RIGHT);
        l.setFont(new Font("Monospaced", Font.BOLD, 16));
        l.setForeground(warna);
        return l;
    }

    private JLabel buatLabelNama(String teks, Color warna) {
        JLabel l = new JLabel(teks);
        l.setFont(new Font("SansSerif", Font.BOLD, 14));
        l.setForeground(warna);
        return l;
    }

    private JButton buatTombol(String teks, Color warna) {
        JButton b = new JButton(teks);
        b.setBackground(warna);
        b.setForeground(BG);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setBorder(BorderFactory.createEmptyBorder(9, 20, 9, 20));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    public void launchFrame() {
        sliderPanel.add(buatLabelNama("Merah (R)", MERAH));
        sliderPanel.add(sliderR);
        sliderPanel.add(lblR);
        sliderPanel.add(buatLabelNama("Hijau (G)", HIJAU));
        sliderPanel.add(sliderG);
        sliderPanel.add(lblG);
        sliderPanel.add(buatLabelNama("Biru (B)", BIRU));
        sliderPanel.add(sliderB);
        sliderPanel.add(lblB);
        buttonPanel.add(btnRandom);
        buttonPanel.add(btnReset);
        buttonPanel.add(btnSalin);

        JPanel isi = (JPanel) frame.getContentPane();
        isi.setBackground(BG);
        isi.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        isi.setLayout(new BorderLayout(10, 12));
        isi.add(previewPanel, BorderLayout.NORTH);
        isi.add(sliderPanel, BorderLayout.CENTER);
        isi.add(buttonPanel, BorderLayout.SOUTH);

        SliderHandler sh = new SliderHandler(this);
        sliderR.addChangeListener(sh);
        sliderG.addChangeListener(sh);
        sliderB.addChangeListener(sh);
        ButtonHandler bh = new ButtonHandler(this);
        btnRandom.addActionListener(bh);
        btnReset.addActionListener(bh);
        btnSalin.addActionListener(bh);
        previewPanel.addMouseListener(new PreviewClickHandler(this));

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new CloseHandler(this));

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void updateWarna() {
        int r = sliderR.getValue(), g = sliderG.getValue(), b = sliderB.getValue();
        previewPanel.setBackground(new Color(r, g, b));
        lblHex.setText(String.format("#%02X%02X%02X", r, g, b));
        lblInfo.setText("RGB(" + r + ", " + g + ", " + b + ")");
        lblR.setText(String.valueOf(r));
        lblG.setText(String.valueOf(g));
        lblB.setText(String.valueOf(b));
        int terang = (r * 299 + g * 587 + b * 114) / 1000;
        Color teks = terang > 128 ? Color.BLACK : Color.WHITE;
        lblHex.setForeground(teks);
        lblInfo.setForeground(teks);
    }

    public void acakWarna() {
        sliderR.setValue(rnd.nextInt(256));
        sliderG.setValue(rnd.nextInt(256));
        sliderB.setValue(rnd.nextInt(256));
    }

    public void resetWarna() {
        sliderR.setValue(0);
        sliderG.setValue(0);
        sliderB.setValue(0);
    }

    public void salinHex() {
        Toolkit.getDefaultToolkit().getSystemClipboard()
                .setContents(new StringSelection(lblHex.getText()), null);
        lblInfo.setText("Kode " + lblHex.getText() + " tersalin ke clipboard!");
        Timer t = new Timer(1500, e -> updateWarna()); // kembalikan teks setelah 1,5 detik
        t.setRepeats(false);
        t.start();
    }

    public JFrame getFrame() {
        return frame;
    }

    public String getKodeHex() {
        return lblHex.getText();
    }
}
