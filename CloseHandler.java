import javax.swing.JOptionPane;
import java.awt.event.*;

public class CloseHandler extends WindowAdapter {
    private MixerFrame mixer;

    public CloseHandler(MixerFrame mixer) {
        this.mixer = mixer;
    }

    public void windowClosing(WindowEvent e) {
        int pilih = JOptionPane.showConfirmDialog(mixer.getFrame(),
                "Yakin ingin keluar?", "Konfirmasi",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (pilih == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
