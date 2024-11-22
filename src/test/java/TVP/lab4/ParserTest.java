package TVP.lab4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {

    @ParameterizedTest
    @CsvSource({
            "1 & 1, true",
            "0 | 1, true",
            "1 = 1, true",
            "0 = 0, true",
            "1 & 0, false",
            "0 | 0, false",
            "1 = 0, false",
            "(1 & 0) | (!0), true",
            "!(1 & 1) = 0, true",
            "(1 | 0) & (!1), false",
            "(0 = 1) | (1 = 1), true",
            "!(0 | 1) & (1 & 1), false",
            "(1 & 1) = (!0), true",
            "((1 & 0) | (0 & 1)) = 0, true",
            "!(1 | (!0)) = 0, true",
            "((!1 | 1) & 0) = 0, true",
            "1 & (0 | !0), true",
            "(0 | (1 & 0)), false",
            "1, true",
            "0, false"
    })
    @DisplayName("Ensure parser works correctly")
    void ensureParserWorksIfCorrectInput(String expr, boolean expected) {
        boolean actual = Parser.parse(expr);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "a",
            "1 & ()",
            "() &",
            "1 === 0",
            "25 = 0",
            "1 && 1",
            "!0!1",
            "00000",
            "1111",
            "1&0&1&",
            "1|0|0|",
            "="
    })
    @DisplayName("Ensure parser throws exception if wrong input")
    void ensureParserThrowsExceptionIfWrongInput(String expr) {
        assertThrows(RuntimeException.class, () -> Parser.parse(expr));
    }
}