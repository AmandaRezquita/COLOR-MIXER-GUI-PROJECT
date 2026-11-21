import javax.swing.event.*;

public class SliderHandler implements ChangeListener {
    private MixerFrame mixer;

    public SliderHandler(MixerFrame mixer) {
        this.mixer = mixer;
    }

    public void stateChanged(ChangeEvent e) {
        mixer.updateWarna();
    }
}
