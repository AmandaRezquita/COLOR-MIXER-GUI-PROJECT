import javax.swing.JOptionPane;
import java.awt.event.*;

public class PreviewClickHandler extends MouseAdapter {
    private MixerFrame mixer;

    public PreviewClickHandler(MixerFrame mixer) {
        this.mixer = mixer;
    }

    public void mouseClicked(MouseEvent e) {
        JOptionPane.showMessageDialog(mixer.getFrame(),
                "Kode warna: " + mixer.getKodeHex(),
                "Info Warna", JOptionPane.INFORMATION_MESSAGE);
    }
}
