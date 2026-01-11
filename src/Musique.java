import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

    public class Musique {
        public static Clip clipMusiqueDebut;

        public static Clip jouer(String nomFichier) throws UnsupportedAudioFileException, IOException, LineUnavailableException  {
            File fichier = new File("son/" + nomFichier);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(fichier);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            return clip;
        }

        public static void musiqueDebut() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
            clipMusiqueDebut = jouer("pacman_beginning.wav");
            clipMusiqueDebut.loop(Clip.LOOP_CONTINUOUSLY);
            FloatControl gainControl = (FloatControl) clipMusiqueDebut.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-25.0f);
        }
        public static void sonMangerCerise()throws UnsupportedAudioFileException, LineUnavailableException, IOException {
            jouer("pacman_eatfruit.wav");
        }
        public static void sonMort() throws UnsupportedAudioFileException, LineUnavailableException, IOException{
            arreterMusique();
            jouer("pacman_death.wav");
        }
        public static void sonMangerFantome() throws UnsupportedAudioFileException, LineUnavailableException, IOException{
            jouer("pacman_eatghost.wav");
        }

        public static void arreterMusique() {
            if (clipMusiqueDebut != null && clipMusiqueDebut.isRunning()) {
                clipMusiqueDebut.stop();
            }
        }

    }


