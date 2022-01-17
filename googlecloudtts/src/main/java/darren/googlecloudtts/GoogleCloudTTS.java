package darren.googlecloudtts;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;

import darren.googlecloudtts.api.SynthesizeApi;
import darren.googlecloudtts.api.VoicesApi;
import darren.googlecloudtts.model.VoicesList;
import darren.googlecloudtts.parameter.AudioConfig;
import darren.googlecloudtts.exception.ApiException;
import darren.googlecloudtts.parameter.VoiceSelectionParams;
import darren.googlecloudtts.parameter.SynthesisInput;
import darren.googlecloudtts.request.SynthesizeRequest;
import darren.googlecloudtts.response.SynthesizeResponse;
import darren.googlecloudtts.response.VoicesResponse;

/**
 * Author: Changemyminds.
 * Date: 2018/6/24.
 * Description:
 * Reference:
 */
public class GoogleCloudTTS implements AutoCloseable  {
    private SynthesizeApi mSynthesizeApi;
    private VoicesApi mVoicesApi;


    private VoiceSelectionParams mVoiceSelectionParams;
    private AudioConfig mAudioConfig;

    private MediaPlayer mMediaPlayer;
    public String passValue;

    private int mVoiceLength = -1;
    String url;
    Context context;
//    GoogleCloudTTS myClass = new GoogleCloudTTS(this);


//    // Constructor
//    GoogleCloudTTS(Context context) {
//        this.context = context;
//    }

    public GoogleCloudTTS(SynthesizeApi synthesizeApi, VoicesApi voicesApi) {
        mSynthesizeApi = synthesizeApi;
        mVoicesApi = voicesApi;
    }


    public GoogleCloudTTS setVoiceSelectionParams(VoiceSelectionParams voiceSelectionParams) {
        mVoiceSelectionParams = voiceSelectionParams;
        return this;
    }

    public GoogleCloudTTS setAudioConfig(AudioConfig audioConfig) {
        mAudioConfig = audioConfig;
        return this;
    }

    public VoicesList load() {
        VoicesResponse response = mVoicesApi.get();
        VoicesList voicesList = new VoicesList();

        for (VoicesResponse.Voices voices : response.getVoices()) {
            String languageCode = voices.getLanguageCodes().get(0);
            VoiceSelectionParams params = new VoiceSelectionParams(
                    languageCode,
                    voices.getName(),
                    voices.getSsmlGender()
            );
            voicesList.add(languageCode, params);
        }

        return voicesList;
    }

    public void start(String text) {
        if (mVoiceSelectionParams == null) {
            throw new NullPointerException("You forget to setVoiceSelectionParams()");
        }

        if (mAudioConfig == null) {
            throw new NullPointerException("You forget to setAudioConfig()");
        }

        SynthesizeRequest request = new SynthesizeRequest(new SynthesisInput(text), mVoiceSelectionParams, mAudioConfig);

        try {
            SynthesizeResponse response = mSynthesizeApi.get(request);
//            playAudio(response.getAudioContent());
//            String uploadaudio = "data:audio/mp3;base64," + response.getAudioContent();
            String uploadaudio = response.getAudioContent();

//            String stringToConvert = "This String is 76 characters long and will be converted to an array of bytes";
//            byte[] theByteArray = uploadaudio.getBytes();
//            String encoded = Base64.encodeToString(theByteArray, 0);
//            Log.e("Encodeddata  old: ", encoded);
//
//            byte[] decoded = Base64.decode(encoded, 0);
//            Log.e("Encodeddata new: ", Arrays.toString(decoded));
//
//            Log.e("Encodeddata olew: ", String.valueOf(request));



            passValue = uploadaudio;

















            ////////////////////////////////////////////////////////////////////

        } catch (Exception e) {
            throw new ApiException(e);
        }
    }

    public void stop() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            Log.e("uuuuuuu1", url);
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mVoiceLength = -1;
        }
    }

    public void pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mVoiceLength = mMediaPlayer.getCurrentPosition();
        }
    }

    public String getena() {
        return passValue;
    }
    public void resume() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying() && mVoiceLength != -1) {
            mMediaPlayer.seekTo(mVoiceLength);
            mMediaPlayer.start();
        }
    }

    private void playAudio(String base64EncodedString) throws IOException {
        stop();
        url = "data:audio/mp3;base64," + base64EncodedString;
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setDataSource(url);
        mMediaPlayer.prepare();
        mMediaPlayer.start();
//        int maxLogSize = 1000;
//        for(int i = 0; i <= url.length() / maxLogSize; i++) {
//            int start = i * maxLogSize;
//            int end = (i+1) * maxLogSize;
//            end = end > url.length() ? url.length() : end;
//            Log.e("uuuuuuu2", url.substring(start, end));
//        }
//        Log.e("uuuuuuu2", url);


//        uploadPithyFromServer(url, "testaudio");
    }

    public void close() {
        stop();
        mMediaPlayer.release();
        mMediaPlayer = null;
    }
    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
