package jcc.operation;

import java.nio.file.*;

import org.antlr.v4.gui.*;

import jnr.posix.*;

class Grun {
    /**
     * @throws Exception
     * 
     */
    public static void main(String[] arguments) throws Exception {
        POSIX posix = POSIXFactory.getPOSIX(new DummyHandler(), true);
        String path = Paths.get("src","main", "antrl").toAbsolutePath().toString();
        System.setProperty("user.dir", path);
        posix.chdir(path);
        System.out.println(path);
        System.out.println(Paths.get("").toAbsolutePath());
        TestRig.main(new String[] { "Operation", "operation", "-tree"});
    }
}