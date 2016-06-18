/* 
 * Copyright 2015 Patrik Karlsson.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package se.trixon.toolbox.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import org.openide.util.Exceptions;

/**
 *
 * @author Patrik Karlsson <patrik@trixon.se>
 */
public class Launcher {

    private boolean mDestroyedByUser;
    private final Set<LauncherListener> mLauncherListeners = new HashSet<>();
    private Process mProcess;
    private final ProcessBuilder mProcessBuilder = new ProcessBuilder(new String[]{"/home/pata/ticktock.sh"});

    public Launcher() {
    }

    public Launcher(LauncherListener launcherListener) {
        mLauncherListeners.add(launcherListener);
    }

    public boolean addLauncherListener(LauncherListener launcherListener) {
        return mLauncherListeners.add(launcherListener);
    }

    public void clearLaunchables() {
        mLauncherListeners.clear();
    }

    public void destroy() {
        mDestroyedByUser = true;
        mProcess.destroy();
    }

    public Process getProcess() {
        return mProcess;
    }

    public ProcessBuilder getProcessBuilder() {
        return mProcessBuilder;
    }

    public void launch() throws IOException, InterruptedException {
        mDestroyedByUser = false;
        mProcess = mProcessBuilder.start();

        new Thread(new MessageProcessor(mProcess.getInputStream(), Mode.STD)).start();
        new Thread(new MessageProcessor(mProcess.getErrorStream(), Mode.ERR)).start();

        mProcess.waitFor();

        for (LauncherListener launcherListener : mLauncherListeners) {
            launcherListener.onLauncherFinished(mProcess.exitValue(), mDestroyedByUser);
        }
    }

    public enum Mode {

        ERR, STD;
    }

    public interface LauncherListener {

        public void launcherLog(String string);

        public void onLauncherFinished(int exitValue, boolean destroyedByUser);

        public void onLauncherMessage(String message, Mode mode);

    }

    class MessageProcessor implements Runnable {

        private final InputStream mInputStream;
        private final Mode mMode;

        public MessageProcessor(InputStream inputStream, Mode mode) {
            mInputStream = inputStream;
            mMode = mode;
        }

        @Override
        public void run() {
            InputStreamReader inputStreamReader = new InputStreamReader(mInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            try {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    for (LauncherListener launcherListener : mLauncherListeners) {
                        launcherListener.onLauncherMessage(line, mMode);
                    }
                }
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
                for (LauncherListener launcherListener : mLauncherListeners) {
                    launcherListener.onLauncherMessage(ex.getLocalizedMessage(), Mode.ERR);
                }
            }
        }
    }
}
