package rpc; /**
 * The CECS327InputStream extends InputStream class. The class implements
 * markers that are used in AudioInputStream
 *
 * @author Oscar Morales-Ponce
 * @version 0.15
 * @since 2019-01-24
 */


import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.concurrent.Semaphore;


public class CECS327InputStream extends InputStream {
    /**
     * Total number of bytes in the file
     */
    protected int total = 0;
    /**
     * Marker
     */
    protected int mark = 0;
    /**
     * Current reading position
     */
    protected int pos = 0;
    /**
     * It stores a buffer with FRAGMENT_SIZE bytes for the current reading.
     * This variable is useful for UDP sockets. Thus bur is the datagram
     */
    protected byte buf[];
    /**
     * It prepares for the nuext buffer. In UDP sockets you can read nextbufer
     * while buf is in use
     */
    protected byte nextBuf[];
    /**
     * It is used to read the buffer
     */
    protected int fragment = 0;
    protected static final int FRAGMENT_SIZE = 8192;
    /**
     * File name to stream
     */
    protected Long fileName;
    /**
     * Instance of an implementation of proxyInterface
     */
    protected ProxyInterface proxy;

    Semaphore sem;
    String streamType;

    /**
     * Constructor of the class. Initialize the variables and reads the first
     * frament in nextBuf
     *
     * @param fileName The name of the file
     */
    public CECS327InputStream(Long fileName, ProxyInterface proxy) throws IOException {
        sem = new Semaphore(1);
        try {
            sem.acquire();
        } catch (InterruptedException exc) {
            System.out.println(exc);
        }
        streamType = "song";
        this.proxy = proxy;
        this.fileName = fileName;
        this.buf = new byte[FRAGMENT_SIZE];
        this.nextBuf = new byte[FRAGMENT_SIZE];
        String[] param = new String[1];
        param[0] = String.valueOf(this.fileName);
        JsonObject jsonRet = proxy.synchExecution("getFileSize", param);
        this.total = Integer.parseInt(jsonRet.get("ret").getAsString());
        getBuff(fragment);
        fragment++;
        System.out.println("CECS327InputStream constructed");//remove
    }

    // For Search Result
    public CECS327InputStream(String query, ProxyInterface proxy) throws IOException {
        sem = new Semaphore(1);
        try {
            sem.acquire();
        } catch (InterruptedException exc) {
            System.out.println(exc.getMessage());
        }
        streamType = "search";
        this.proxy = proxy;
        this.buf = new byte[FRAGMENT_SIZE];
        this.nextBuf = new byte[FRAGMENT_SIZE];
        String[] param = new String[1];
        param[0] = query;
        JsonObject jsonRet = proxy.synchExecution("getSize", param);
        this.total = Integer.parseInt(jsonRet.get("ret").getAsString());
        getBuff(fragment);
        fragment++;
    }

    // For login
    public CECS327InputStream(String username, String password, ProxyInterface proxy) throws IOException {
        sem = new Semaphore(1);
        try {
            sem.acquire();
        } catch (InterruptedException exc) {
            System.out.println(exc.getMessage());
        }
        streamType = "login";
        this.proxy = proxy;
        this.buf = new byte[FRAGMENT_SIZE];
        this.nextBuf = new byte[FRAGMENT_SIZE];
        String[] param = new String[2];
        param[0] = username;
        param[1] = password;
        JsonObject jsonRet = proxy.synchExecution("login", param);
        this.total = FRAGMENT_SIZE;
        String s = jsonRet.get("ret").getAsString();
        nextBuf = Base64.getDecoder().decode(s);
        sem.release();
        System.out.println("Read Buffer");
    }

    // For retrieving playlists
    public CECS327InputStream(Integer userToken, ProxyInterface proxy) throws IOException {
        sem = new Semaphore(1);
        try {
            sem.acquire();
        } catch (InterruptedException exc) {
            System.out.println(exc);
        }
        streamType = "retrievePlaylists";
        this.proxy = proxy;
        this.buf = new byte[FRAGMENT_SIZE];
        this.nextBuf = new byte[FRAGMENT_SIZE];
        String[] param = new String[1];
        param[0] = Integer.toString(userToken);
        JsonObject jsonRet = proxy.synchExecution("getPlaylistsSize", param);
        this.total = Integer.parseInt(jsonRet.get("ret").getAsString());
        getBuff(fragment);
        fragment++;
    }

    /**
     * getNextBuff reads the buffer. It gets the data using
     * the remote method getSongChunk
     */
    protected void getBuff(int fragment) throws IOException {
        if (streamType.equals("song")) {
            new Thread() {
                public void run() {
                    String[] param = new String[2];
                    param[0] = String.valueOf(fileName);
                    param[1] = String.valueOf(fragment);

                    JsonObject jsonRet = proxy.synchExecution("getSongChunk", param);
                    String s = jsonRet.get("ret").getAsString();
                    nextBuf = Base64.getDecoder().decode(s);
                    sem.release();
                    System.out.println("Read buffer");
                }
            }.start();
        } else if (streamType.equals("search")) {
            new Thread() {
                public void run() {
                    String[] param = new String[1];
                    param[0] = String.valueOf(fragment);

                    JsonObject jsonRet = proxy.synchExecution("getSearchResultChunk", param);
                    String s = jsonRet.get("ret").getAsString();
                    nextBuf = Base64.getDecoder().decode(s);
                    sem.release();
                    System.out.println("Read search buffer");
                }
            }.start();
        } else if (streamType.equals("retrievePlaylists")) {
            new Thread() {
                public void run() {
                    String[] param = new String[1];
                    param[0] = String.valueOf(fragment);

                    JsonObject jsonRet = proxy.synchExecution("getPlaylistsChunk", param);
                    String s = jsonRet.get("ret").getAsString();
                    nextBuf = Base64.getDecoder().decode(s);
                    sem.release();
                    System.out.println("Read getplaylists buffer");
                }
            }.start();
        }
    }


    /**
     * Reads the next byte of data from the input stream.
     */
    @Override
    public synchronized int read() throws IOException {


        if (pos >= total) {
            pos = 0;
            return -1;
        }
        int posmod = pos % FRAGMENT_SIZE;
        if (posmod == 0) {
            try {
                sem.acquire();
            } catch (InterruptedException exc) {
                System.out.println(exc);
            }
            for (int i = 0; i < FRAGMENT_SIZE; i++)
                buf[i] = nextBuf[i];

            getBuff(fragment);
            fragment++;
        }
        int p = pos % FRAGMENT_SIZE;
        pos++;
        return buf[p] & 0xff;
    }

    /**
     * Reads some number of bytes from the input stream and stores them
     * into the buffer array b.
     */
    @Override
    public synchronized int read(byte b[], int off, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        }

        if (pos >= total) {
            return -1;
        }
        int avail = total - pos;
        if (len > avail) {
            len = avail;
        }
        if (len <= 0) {
            return 0;
        }
        for (int i = off; i < off + len; i++)
            b[i] = (byte) read();
        return len;
    }

    /**
     * Skips over and discards n bytes of data from this input stream.
     */
    @Override
    public synchronized long skip(long n) throws IOException {
        long k = total - pos;
        if (n < k) {
            k = n < 0 ? 0 : n;
        }

        pos += k;
        fragment = (int) Math.floor(pos / FRAGMENT_SIZE);
        getBuff(fragment);
        fragment++;
        getBuff(fragment);
        return k;
    }

    /**
     * Returns an estimate of the number of bytes that can be read
     * (or skipped over) from this input stream without blocking by
     * the next invocation of a method for this input stream.
     */
    @Override
    public synchronized int available() {
        return total - pos;
    }

    /**
     * Tests if this input stream supports the mark and reset methods.
     */
    @Override
    public boolean markSupported() {
        return true;
    }

    /**
     * Marks the current position in this input stream.
     */
    @Override
    public void mark(int readAheadLimit) {
        mark = pos;
    }

    /**
     * Repositions this stream to the position at the time the
     * mark method was last called on this input stream.
     */
    @Override
    public synchronized void reset() throws IOException {
        pos = mark;
        fragment = (int) Math.floor(pos / FRAGMENT_SIZE);
        getBuff(fragment);
        fragment++;
        getBuff(fragment);
    }

    /**
     * Closes this input stream and releases any system resources
     * associated with the stream.
     */
    @Override
    public void close() throws IOException {
        fragment = 0;
        System.out.print("Steam of type " + streamType + " closed.");
    }

}