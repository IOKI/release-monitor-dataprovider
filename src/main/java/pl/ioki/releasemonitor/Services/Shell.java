package pl.ioki.releasemonitor.Services;

import org.springframework.stereotype.Component;
import pl.ioki.releasemonitor.Exceptions.ShellException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class Shell {
    public String executeCommand(String command) {
        String output = "";

        Process process;
        try {
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
            String errors = this.getOutput(process.getErrorStream());
            if (process.exitValue() == 0 && errors.isEmpty()) {
                output = this.getOutput(process.getInputStream());
            } else {
                throw new ShellException("Execution of command failed: " + errors);
            }

        } catch (Exception e) {
            throw new ShellException(e.getMessage(), e);
        }

        return output;
    }

    private String getOutput(InputStream stream) {
        StringBuffer output = new StringBuffer();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
        } catch (Exception e) {
            throw new ShellException(e.getMessage(), e);
        }

        return output.toString();
    }
}
