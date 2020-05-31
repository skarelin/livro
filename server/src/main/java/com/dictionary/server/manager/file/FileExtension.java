package com.dictionary.server.manager.file;

enum FileExtension {
    PDF("pdf"),
    EPUB("epub"),
    TXT("txt");

    private final String extension;

    FileExtension(String s) {
        extension = s;
    }

    public String getValue() {
        return extension;
    }

    public static FileExtension fromString(String type) {
        for (FileExtension extension : FileExtension.values()) {
            if (extension.extension.equalsIgnoreCase(type)) {
                return extension;
            }
        }
        throw new FileExtensionException("File type is not supported: " + type);
    }
}
