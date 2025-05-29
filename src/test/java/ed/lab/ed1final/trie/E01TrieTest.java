package ed.lab.ed1final.trie;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class E01TrieTest {

    @ParameterizedTest
    @MethodSource("testArguments")
    void test(List<TrieOperation> trieOperations) {

        Trie trie = new Trie();
        List<TrieOperation> previousOps = new ArrayList<>();

        for (TrieOperation trieOperation : trieOperations) {
            Integer actualValue = operationsMap.get(trieOperation.name())
                    .apply(trie, trieOperation);

            previousOps.add(trieOperation);

            if (actualValue != null) {
                assertEquals(trieOperation.expected(), actualValue,
                        () -> String.format("""
                                Error al llamar a %s(). Se esperaba %s y se obtuvo %s.
                                Lista de operaciones previas:
                                %s
                                """,
                                trieOperation.name(),
                                trieOperation.expected(),
                                actualValue,
                                previousOps.stream()
                                        .map(String::valueOf)
                                        .collect(Collectors.joining("\n"))
                        ));
            }
        }
    }

    private static Stream<List<TrieOperation>> testArguments() {
        try {
            final String fileContent = new String(
                    Objects.requireNonNull(E01TrieTest.class.getClassLoader()
                                    .getResourceAsStream("ed/lab/E01.csv"))
                            .readAllBytes());

            String[] lines = fileContent.split("\n");

            Stream.Builder<List<TrieOperation>> builder = Stream.builder();
            List<TrieOperation> operations = new ArrayList<>();

            for (String line : lines) {
                if (line.isBlank()) {
                    builder.add(operations);
                    operations = new ArrayList<>();
                    continue;
                }

                String[] tokens = line.split(",");

                String name = tokens[0];
                String arg = tokens[1];
                Integer expected = tokens.length > 2 ? Integer.parseInt(tokens[2].trim()) : null;

                operations.add(new TrieOperation(name, arg, expected));
            }

            if (!operations.isEmpty())
                builder.add(operations);

            return builder.build();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Map<String, BiFunction<Trie,TrieOperation, Integer>> operationsMap = Map.of(
            "countWordsEqualTo", (trie, trieOperation) -> trie.countWordsEqualTo(trieOperation.arg()),
            "countWordsStartingWith", (trie, trieOperation) -> trie.countWordsStartingWith(trieOperation.arg()),
            "insert", ((trie, trieOperation) -> {
                trie.insert(trieOperation.arg());
                return null;
            }),
            "erase", (trie, trieOperation) -> {
                trie.erase(trieOperation.arg());
                return null;
            }
    );

    private record TrieOperation(String name, String arg, Integer expected) {

        @Override
        public String toString() {
            return String.format("%s(%s)%s",
                    name,
                    arg != null ? arg : "",
                    expected != null ? " -> " + expected : "");
        }
    }
}