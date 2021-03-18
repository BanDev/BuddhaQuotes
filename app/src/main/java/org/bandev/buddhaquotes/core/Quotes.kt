/**

Buddha Quotes
Copyright (C) 2021  BanDev

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.

 */

package org.bandev.buddhaquotes.core

import android.content.Context
import org.bandev.buddhaquotes.R

/**
 * Quotes manages all of our quotes
 *
 * @author jack.txt & Fennec_exe
 * @since v1.2.0
 * @updated 09/12/2020
 */

class Quotes {

    var quotenumberglobal: Int = 0
    private var maxQuote: Int = 237

    /**
     * Returns the quote with the corresponding index (0 for a random quote)
     *
     * @param [Quote_Number] is the index of the requested quote (Int)
     * @param [context] context of activity (Context)
     * @return the quote (String)
     */

    fun getQuote(Quote_Number: Int, context: Context): String {
        val num: Int = if (Quote_Number == 0) {
            getRandomIntegerBetweenRange(1.0, maxQuote.toDouble()).toInt()
        } else {
            Quote_Number
        }
        var text = ""
        when (num) {
            1 -> text = context.getString(R.string.quote_1)
            2 -> text = context.getString(R.string.quote_2)
            3 -> text = context.getString(R.string.quote_3)
            4 -> text = context.getString(R.string.quote_4)
            5 -> text = context.getString(R.string.quote_5)
            6 -> text = context.getString(R.string.quote_6)
            7 -> text = context.getString(R.string.quote_7)
            8 -> text = context.getString(R.string.quote_8)
            9 -> text = context.getString(R.string.quote_9)
            10 -> text = context.getString(R.string.quote_10)
            11 -> text = context.getString(R.string.quote_11)
            12 -> text = context.getString(R.string.quote_12)
            13 -> text = context.getString(R.string.quote_13)
            14 -> text = context.getString(R.string.quote_14)
            15 -> text = context.getString(R.string.quote_15)
            16 -> text = context.getString(R.string.quote_16)
            17 -> text = context.getString(R.string.quote_17)
            18 -> text = context.getString(R.string.quote_18)
            19 -> text = context.getString(R.string.quote_19)
            20 -> text = context.getString(R.string.quote_20)
            21 -> text = context.getString(R.string.quote_21)
            22 -> text = context.getString(R.string.quote_22)
            23 -> text = context.getString(R.string.quote_23)
            24 -> text = context.getString(R.string.quote_24)
            25 -> text = context.getString(R.string.quote_25)
            26 -> text = context.getString(R.string.quote_26)
            27 -> text = context.getString(R.string.quote_27)
            28 -> text = context.getString(R.string.quote_28)
            29 -> text = context.getString(R.string.quote_29)
            30 -> text = context.getString(R.string.quote_30)
            31 -> text = context.getString(R.string.quote_31)
            32 -> text = context.getString(R.string.quote_32)
            33 -> text = context.getString(R.string.quote_33)
            34 -> text = context.getString(R.string.quote_34)
            35 -> text = context.getString(R.string.quote_35)
            36 -> text = context.getString(R.string.quote_36)
            37 -> text = context.getString(R.string.quote_37)
            38 -> text = context.getString(R.string.quote_38)
            39 -> text = context.getString(R.string.quote_39)
            40 -> text = context.getString(R.string.quote_40)
            41 -> text = context.getString(R.string.quote_41)
            42 -> text = context.getString(R.string.quote_42)
            43 -> text = context.getString(R.string.quote_43)
            44 -> text = context.getString(R.string.quote_44)
            45 -> text = context.getString(R.string.quote_45)
            46 -> text = context.getString(R.string.quote_46)
            47 -> text = context.getString(R.string.quote_47)
            48 -> text = context.getString(R.string.quote_48)
            49 -> text = context.getString(R.string.quote_49)
            50 -> text = context.getString(R.string.quote_50)
            51 -> text = context.getString(R.string.quote_51)
            52 -> text = context.getString(R.string.quote_52)
            53 -> text = context.getString(R.string.quote_53)
            54 -> text = context.getString(R.string.quote_54)
            55 -> text = context.getString(R.string.quote_55)
            56 -> text = context.getString(R.string.quote_56)
            57 -> text = context.getString(R.string.quote_57)
            58 -> text = context.getString(R.string.quote_58)
            59 -> text = context.getString(R.string.quote_59)
            60 -> text = context.getString(R.string.quote_60)
            61 -> text = context.getString(R.string.quote_61)
            62 -> text = context.getString(R.string.quote_62)
            63 -> text = context.getString(R.string.quote_63)
            64 -> text = context.getString(R.string.quote_64)
            65 -> text = context.getString(R.string.quote_65)
            66 -> text = context.getString(R.string.quote_66)
            67 -> text = context.getString(R.string.quote_67)
            68 -> text = context.getString(R.string.quote_68)
            69 -> text = context.getString(R.string.quote_69)
            70 -> text = context.getString(R.string.quote_70)
            71 -> text = context.getString(R.string.quote_71)
            72 -> text = context.getString(R.string.quote_72)
            73 -> text = context.getString(R.string.quote_73)
            74 -> text = context.getString(R.string.quote_74)
            75 -> text = context.getString(R.string.quote_75)
            76 -> text = context.getString(R.string.quote_76)
            77 -> text = context.getString(R.string.quote_77)
            78 -> text = context.getString(R.string.quote_78)
            79 -> text = context.getString(R.string.quote_79)
            80 -> text = context.getString(R.string.quote_80)
            81 -> text = context.getString(R.string.quote_81)
            82 -> text = context.getString(R.string.quote_82)
            83 -> text = context.getString(R.string.quote_83)
            84 -> text = context.getString(R.string.quote_84)
            85 -> text = context.getString(R.string.quote_85)
            86 -> text = context.getString(R.string.quote_86)
            87 -> text = context.getString(R.string.quote_87)
            88 -> text = context.getString(R.string.quote_88)
            89 -> text = context.getString(R.string.quote_89)
            90 -> text = context.getString(R.string.quote_90)
            91 -> text = context.getString(R.string.quote_91)
            92 -> text = context.getString(R.string.quote_92)
            93 -> text = context.getString(R.string.quote_93)
            94 -> text = context.getString(R.string.quote_94)
            95 -> text = context.getString(R.string.quote_95)
            96 -> text = context.getString(R.string.quote_96)
            97 -> text = context.getString(R.string.quote_97)
            98 -> text = context.getString(R.string.quote_98)
            99 -> text = context.getString(R.string.quote_99)
            100 -> text = context.getString(R.string.quote_100)
            101 -> text = context.getString(R.string.quote_101)
            102 -> text = context.getString(R.string.quote_102)
            103 -> text = context.getString(R.string.quote_103)
            104 -> text = context.getString(R.string.quote_104)
            105 -> text = context.getString(R.string.quote_105)
            106 -> text = context.getString(R.string.quote_106)
            107 -> text = context.getString(R.string.quote_107)
            108 -> text = context.getString(R.string.quote_108)
            109 -> text = context.getString(R.string.quote_109)
            110 -> text = context.getString(R.string.quote_110)
            111 -> text = context.getString(R.string.quote_111)
            112 -> text = context.getString(R.string.quote_112)
            113 -> text = context.getString(R.string.quote_113)
            114 -> text = context.getString(R.string.quote_114)
            115 -> text = context.getString(R.string.quote_115)
            116 -> text = context.getString(R.string.quote_116)
            117 -> text = context.getString(R.string.quote_117)
            118 -> text = context.getString(R.string.quote_118)
            119 -> text = context.getString(R.string.quote_119)
            120 -> text = context.getString(R.string.quote_120)
            121 -> text = context.getString(R.string.quote_121)
            122 -> text = context.getString(R.string.quote_122)
            123 -> text = context.getString(R.string.quote_123)
            124 -> text = context.getString(R.string.quote_124)
            125 -> text = context.getString(R.string.quote_125)
            126 -> text = context.getString(R.string.quote_126)
            127 -> text = context.getString(R.string.quote_127)
            128 -> text = context.getString(R.string.quote_128)
            129 -> text = context.getString(R.string.quote_129)
            130 -> text = context.getString(R.string.quote_130)
            131 -> text = context.getString(R.string.quote_131)
            132 -> text = context.getString(R.string.quote_132)
            133 -> text = context.getString(R.string.quote_133)
            134 -> text = context.getString(R.string.quote_134)
            135 -> text = context.getString(R.string.quote_135)
            136 -> text = context.getString(R.string.quote_136)
            137 -> text = context.getString(R.string.quote_137)
            138 -> text = context.getString(R.string.quote_138)
            139 -> text = context.getString(R.string.quote_139)
            140 -> text = context.getString(R.string.quote_140)
            141 -> text = context.getString(R.string.quote_141)
            142 -> text = context.getString(R.string.quote_142)
            143 -> text = context.getString(R.string.quote_143)
            144 -> text = context.getString(R.string.quote_144)
            145 -> text = context.getString(R.string.quote_145)
            146 -> text = context.getString(R.string.quote_146)
            147 -> text = context.getString(R.string.quote_147)
            148 -> text = context.getString(R.string.quote_148)
            149 -> text = context.getString(R.string.quote_149)
            150 -> text = context.getString(R.string.quote_150)
            151 -> text = context.getString(R.string.quote_151)
            152 -> text = context.getString(R.string.quote_152)
            153 -> text = context.getString(R.string.quote_153)
            154 -> text = context.getString(R.string.quote_154)
            155 -> text = context.getString(R.string.quote_155)
            156 -> text = context.getString(R.string.quote_156)
            157 -> text = context.getString(R.string.quote_157)
            158 -> text = context.getString(R.string.quote_158)
            159 -> text = context.getString(R.string.quote_159)
            160 -> text = context.getString(R.string.quote_160)
            161 -> text = context.getString(R.string.quote_161)
            162 -> text = context.getString(R.string.quote_162)
            163 -> text = context.getString(R.string.quote_163)
            164 -> text = context.getString(R.string.quote_164)
            165 -> text = context.getString(R.string.quote_165)
            166 -> text = context.getString(R.string.quote_166)
            167 -> text = context.getString(R.string.quote_167)
            168 -> text = context.getString(R.string.quote_168)
            169 -> text = context.getString(R.string.quote_169)
            170 -> text = context.getString(R.string.quote_170)
            171 -> text = context.getString(R.string.quote_171)
            172 -> text = context.getString(R.string.quote_172)
            173 -> text = context.getString(R.string.quote_173)
            174 -> text = context.getString(R.string.quote_174)
            175 -> text = context.getString(R.string.quote_175)
            176 -> text = context.getString(R.string.quote_176)
            177 -> text = context.getString(R.string.quote_177)
            178 -> text = context.getString(R.string.quote_178)
            179 -> text = context.getString(R.string.quote_179)
            180 -> text = context.getString(R.string.quote_180)
            181 -> text = context.getString(R.string.quote_181)
            182 -> text = context.getString(R.string.quote_182)
            183 -> text = context.getString(R.string.quote_183)
            184 -> text = context.getString(R.string.quote_184)
            185 -> text = context.getString(R.string.quote_185)
            186 -> text = context.getString(R.string.quote_186)
            187 -> text = context.getString(R.string.quote_187)
            188 -> text = context.getString(R.string.quote_188)
            189 -> text = context.getString(R.string.quote_189)
            190 -> text = context.getString(R.string.quote_190)
            191 -> text = context.getString(R.string.quote_191)
            192 -> text = context.getString(R.string.quote_192)
            193 -> text = context.getString(R.string.quote_193)
            194 -> text = context.getString(R.string.quote_194)
            195 -> text = context.getString(R.string.quote_195)
            196 -> text = context.getString(R.string.quote_196)
            197 -> text = context.getString(R.string.quote_197)
            198 -> text = context.getString(R.string.quote_198)
            199 -> text = context.getString(R.string.quote_199)
            200 -> text = context.getString(R.string.quote_200)
            201 -> text = context.getString(R.string.quote_201)
            202 -> text = context.getString(R.string.quote_202)
            203 -> text = context.getString(R.string.quote_203)
            204 -> text = context.getString(R.string.quote_204)
            205 -> text = context.getString(R.string.quote_205)
            206 -> text = context.getString(R.string.quote_206)
            207 -> text = context.getString(R.string.quote_207)
            208 -> text = context.getString(R.string.quote_208)
            209 -> text = context.getString(R.string.quote_209)
            210 -> text = context.getString(R.string.quote_210)
            211 -> text = context.getString(R.string.quote_211)
            212 -> text = context.getString(R.string.quote_212)
            213 -> text = context.getString(R.string.quote_213)
            214 -> text = context.getString(R.string.quote_214)
            215 -> text = context.getString(R.string.quote_215)
            216 -> text = context.getString(R.string.quote_216)
            217 -> text = context.getString(R.string.quote_217)
            218 -> text = context.getString(R.string.quote_218)
            219 -> text = context.getString(R.string.quote_219)
            220 -> text = context.getString(R.string.quote_220)
            221 -> text = context.getString(R.string.quote_221)
            222 -> text = context.getString(R.string.quote_222)
            223 -> text = context.getString(R.string.quote_223)
            224 -> text = context.getString(R.string.quote_224)
            225 -> text = context.getString(R.string.quote_225)
            226 -> text = context.getString(R.string.quote_226)
            227 -> text = context.getString(R.string.quote_227)
            228 -> text = context.getString(R.string.quote_228)
            229 -> text = context.getString(R.string.quote_229)
            230 -> text = context.getString(R.string.quote_230)
            231 -> text = context.getString(R.string.quote_231)
            232 -> text = context.getString(R.string.quote_232)
            233 -> text = context.getString(R.string.quote_233)
            234 -> text = context.getString(R.string.quote_234)
            235 -> text = context.getString(R.string.quote_235)
            236 -> text = context.getString(R.string.quote_236)
            237 -> text = context.getString(R.string.quote_237)

        }
        quotenumberglobal = num
        return text
    }

    companion object {
        fun getRandomIntegerBetweenRange(min: Double, max: Double): Double {
            return (Math.random() * (max - min + 1)).toInt() + min
        }
    }
}