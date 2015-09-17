package se.frand.app.dieapp;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

/*
 * https://openclipart.org/detail/29527/red-die-1
 * http://soundbible.com/182-Shake-And-Roll-Dice.html
 */
public class MainActivity extends Activity {
    protected static final String TAG = "DIE";

    Random r;
    AudioManager mAudioManager;
    SoundPool dice_sound = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
    int sound_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.container);

        final ImageView dieView = new ImageView(getApplicationContext());
        dieView.setImageDrawable(getResources().getDrawable(R.drawable.die1_300px));

        r = new Random();

        dieView.setEnabled(false);

        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        sound_id = dice_sound.load(this,R.raw.shake_dice,1);

        dice_sound.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (0 == status) {
                    dieView.setEnabled(true);
                } else {
                    Log.d(TAG, "Unable to load sound");
                }
            }
        });

        dieView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDrawable(dieView, roll(r));
                dice_sound.play(sound_id,0.99f,0.99f,1,0,1.0f);
            }
        });

        // thanks aporter
        int width = (int) getResources().getDimension(R.dimen.image_width);
        int height = (int) getResources().getDimension(R.dimen.image_height);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                width, height);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);

        dieView.setLayoutParams(params);

        relativeLayout.addView(dieView);
    }

    /*
     * return: id of drawable
     */
    private int roll(Random r) {
        int roll = r.nextInt(6) + 1;
        int id = R.drawable.die1_300px;
        switch (roll) {
            case 1:
                id = R.drawable.die1_300px;
                break;
            case 2:
                id = R.drawable.die2_300px;
                break;
            case 3:
                id = R.drawable.die3_300px;
                break;
            case 4:
                id = R.drawable.die4_300px;
                break;
            case 5:
                id = R.drawable.die5_300px;
                break;
            case 6:
                id = R.drawable.die6_300px;
                break;
        }
        return id;
    }

    private void setDrawable(ImageView v, int id) {
        v.setImageDrawable(getResources().getDrawable(id));
    }


}
