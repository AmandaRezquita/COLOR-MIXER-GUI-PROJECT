import java.awt.event.*;

public class ButtonHandler implements ActionListener {
    private MixerFrame mixer;

    public ButtonHandler(MixerFrame mixer) {
        this.mixer = mixer;
    }

    public void actionPerformed(ActionEvent e) {
        String perintah = e.getActionCommand();
        if (perintah.equals("acak")) {
            mixer.acakWarna();
        } else if (perintah.equals("reset")) {
            mixer.resetWarna();
        } else if (perintah.equals("salin")) {
            mixer.salinHex();
        }
    }
}
