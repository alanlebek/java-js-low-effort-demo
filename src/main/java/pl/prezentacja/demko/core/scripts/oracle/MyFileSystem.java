package pl.prezentacja.demko.core.scripts.oracle;

import org.graalvm.polyglot.io.FileSystem;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.util.Map;
import java.util.Set;

public class MyFileSystem implements FileSystem {
    private String secretPath = "superSecret";
    private Path path = Paths.get("./secret/secret.js");

    private FileSystem defaultFileSystem = FileSystem.newDefaultFileSystem();

    @Override
    public Path parsePath(URI uri) {
        if (secretPath.equals(uri.toString())) {
            return path;
        }

        return defaultFileSystem.parsePath(uri);
    }

    @Override
    public Path parsePath(String path) {
        if (secretPath.equals(path)) {
            return this.path;
        }

        return defaultFileSystem.parsePath(path);
    }

    @Override
    public void checkAccess(Path path, Set<? extends AccessMode> modes, LinkOption... linkOptions) throws IOException {
        defaultFileSystem.checkAccess(path, modes, linkOptions);
    }

    @Override
    public void createDirectory(Path dir, FileAttribute<?>... attrs) throws IOException {
        defaultFileSystem.createDirectory(dir, attrs);
    }

    @Override
    public void delete(Path path) throws IOException {
        defaultFileSystem.delete(path);
    }

    @Override
    public SeekableByteChannel newByteChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs) throws IOException {
        if (this.path.toRealPath().equals(path.toRealPath())) {
            return new RandomAccessFile(path.toFile(), "r").getChannel();
        }
        return defaultFileSystem.newByteChannel(path, options, attrs);
    }

    @Override
    public DirectoryStream<Path> newDirectoryStream(Path dir, DirectoryStream.Filter<? super Path> filter) throws IOException {
        return defaultFileSystem.newDirectoryStream(path, filter);
    }

    @Override
    public Path toAbsolutePath(Path path) {
        return defaultFileSystem.toAbsolutePath(path);
    }

    @Override
    public Path toRealPath(Path path, LinkOption... linkOptions) throws IOException {
        return defaultFileSystem.toRealPath(path, linkOptions);
    }

    @Override
    public Map<String, Object> readAttributes(Path path, String attributes, LinkOption... options) throws IOException {
        return defaultFileSystem.readAttributes(path, attributes, options);
    }
}
