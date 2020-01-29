package jcc.operation;

import java.io.*;
import java.util.*;
import jnr.posix.*;
import jnr.constants.platform.*;

import static java.util.stream.Collectors.toList;

public class DummyHandler implements POSIXHandler {

    @Override
    public void error(Errno error, String extraData) {
        System.err.printf("%s %s\n", error, extraData);
    }

    @Override
    public void error(Errno error, String methodName, String extraData) {
        System.err.printf("%s %s %s\n", error, methodName, extraData);
    }

    @Override
    public void unimplementedError(String methodName) {
        System.err.printf("%s\n", methodName);
    }

    @Override
    public void warn(WARNING_ID id, String message, Object... data) {
        System.err.printf("%s %s %s\n", id, message, data);
    }

    @Override
    public boolean isVerbose() {
        return false;
    }

    @Override
    public File getCurrentWorkingDirectory() {
        return new File(System.getProperty("user.dir"));
    }

    @Override
    public String[] getEnv() {
        return System.getenv()
                     .entrySet()
                     .stream()
                     .map(Map.Entry::toString)
                     .collect(toList())
                     .toArray(new String[0]);
    }

    @Override
    public InputStream getInputStream() {
        return System.in;
    }

    @Override
    public PrintStream getOutputStream() {
        return System.out;
    }

    @Override
    public int getPID() {
        return 0;
    }

    @Override
    public PrintStream getErrorStream() {
        return System.err;
    }
}