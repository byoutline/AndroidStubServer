package com.byoutline.androidstubserver.internal

import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author Sebastian Kacprzak <sebastian.kacprzak at byoutline.com>
 */
class ImageGeneratorPathParsingSpec extends Specification {
    @Unroll
    def "should return #expResult for path: #path"() {
        given:
        String firstC = null
        String secondC = null
        ColorParser colorParser = new ColorParser() {
            @Override
            int parseColor(String c) {
                if (c.contains("invalid")) {
                    throw new IllegalArgumentException()
                }
                if (firstC == null) {
                    firstC = c
                } else {
                    secondC = c
                }
                return 3
            }
        }
        String log = ""
        LogWrap logWrap = new LogWrap() {
            @Override
            void d(String msg) {
                log += msg
            }
        }
        when:
        def result = ImageGenerator.parsePath(path, colorParser, logWrap)
        then:
        result == expResult
        firstC == expFColor
        secondC == expSColor
        where:
        path                | expFColor | expSColor | expResult
        ""                  | null      | null      | null
        "invalid"           | null      | null      | null
        "1x1/invalid"       | "#222222" | "#AAAAAA" | imgInfo(1, 1, "1x1")
        "100x100"           | "#222222" | "#AAAAAA" | imgInfo(100, 100, "100x100")
        "1x2/554433"        | "#554433" | "#AAAAAA" | imgInfo(1, 2, "1x2")
        "1x2/000000/ffffff" | "#000000" | "#ffffff" | imgInfo(1, 2, "1x2")
    }

    private static ImageInfo imgInfo(int width, int height, String text) {
        new ImageInfo(width, height, 3, text, 3)
    }
}
