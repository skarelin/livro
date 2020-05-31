package com.dictionary.server.library.publiclibrary;

import java.util.HashMap;
import java.util.Map;

class PublicBookIdConverter {

    private static Map<Integer, String> idNameMap;
    private static Map<Integer, String> idFileNameMap;

    static {
        idNameMap = new HashMap<>();
        idNameMap.put(1, "[Public]: The Picture of Dorian Gray");
        idNameMap.put(2, "[Public]: Narrative of the Life of Frederick Douglass");
        idNameMap.put(3, "[Public]: The Treasure Train");
        idNameMap.put(4, "[Public]: The Way We Live Now");
        idNameMap.put(5, "[Public]: The Destroying Angel");

        idFileNameMap = new HashMap<>();
        idFileNameMap.put(1, "c7d6bc7d-8977-48e3-bfc6-8d1ce2b34f12.txt");
        idFileNameMap.put(2, "f260e52a-1153-4c1a-9f67-0ab29c12ea71.txt");
        idFileNameMap.put(3, "586d5ad2-587e-4810-a8bd-36e46c58cfa1.txt");
        idFileNameMap.put(4, "ef289de9-89b9-48f4-97ac-cbebb2c0188b.txt");
        idFileNameMap.put(5, "488ef85a-ea9b-4177-a910-08ceacf5f71b.txt");
    }

    static String toBookName(Integer bookId) {
        return idNameMap.get(bookId);
    }

    static String toFileName(Integer bookId) {
        return idFileNameMap.get(bookId);
    }
}
