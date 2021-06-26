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

package org.bandev.buddhaquotes.architecture

import org.bandev.buddhaquotes.R

/**
 * Find a quote based on it's database
 * id
 */

class QuoteFinder {

    /** Get a quote resource */
    fun resource(id: Int): Int {
        return quotes.getValue(id)
    }

    /** Hash map of all quotes in the app */
    var quotes: Map<Int, Int> = mapOf(
        1 to R.string.quote_1, 2 to R.string.quote_2, 3 to R.string.quote_3,
        4 to R.string.quote_4, 5 to R.string.quote_5, 6 to R.string.quote_6,
        7 to R.string.quote_7, 8 to R.string.quote_8, 9 to R.string.quote_9,
        10 to R.string.quote_10, 11 to R.string.quote_11, 12 to R.string.quote_12,
        13 to R.string.quote_13, 14 to R.string.quote_14, 15 to R.string.quote_15,
        16 to R.string.quote_16, 17 to R.string.quote_17, 18 to R.string.quote_18,
        19 to R.string.quote_19, 20 to R.string.quote_20, 21 to R.string.quote_21,
        22 to R.string.quote_22, 23 to R.string.quote_23, 24 to R.string.quote_24,
        25 to R.string.quote_25, 26 to R.string.quote_26, 27 to R.string.quote_27,
        28 to R.string.quote_28, 29 to R.string.quote_29, 30 to R.string.quote_30,
        31 to R.string.quote_31, 32 to R.string.quote_32, 33 to R.string.quote_33,
        34 to R.string.quote_34, 35 to R.string.quote_35, 36 to R.string.quote_36,
        37 to R.string.quote_37, 38 to R.string.quote_38, 39 to R.string.quote_39,
        40 to R.string.quote_40, 41 to R.string.quote_41, 42 to R.string.quote_42,
        43 to R.string.quote_43, 44 to R.string.quote_44, 45 to R.string.quote_45,
        46 to R.string.quote_46, 47 to R.string.quote_47, 48 to R.string.quote_48,
        49 to R.string.quote_49, 50 to R.string.quote_50, 51 to R.string.quote_51,
        52 to R.string.quote_52, 53 to R.string.quote_53, 54 to R.string.quote_54,
        55 to R.string.quote_55, 56 to R.string.quote_56, 57 to R.string.quote_57,
        58 to R.string.quote_58, 59 to R.string.quote_59, 60 to R.string.quote_60,
        61 to R.string.quote_61, 62 to R.string.quote_62, 63 to R.string.quote_63,
        64 to R.string.quote_64, 65 to R.string.quote_65, 66 to R.string.quote_66,
        67 to R.string.quote_67, 68 to R.string.quote_68, 69 to R.string.quote_69,
        70 to R.string.quote_70, 71 to R.string.quote_71, 72 to R.string.quote_72,
        73 to R.string.quote_73, 74 to R.string.quote_74, 75 to R.string.quote_75,
        76 to R.string.quote_76, 77 to R.string.quote_77, 78 to R.string.quote_78,
        79 to R.string.quote_79, 80 to R.string.quote_80, 81 to R.string.quote_81,
        82 to R.string.quote_82, 83 to R.string.quote_83, 84 to R.string.quote_84,
        85 to R.string.quote_85, 86 to R.string.quote_86, 87 to R.string.quote_87,
        88 to R.string.quote_88, 89 to R.string.quote_89, 90 to R.string.quote_90,
        91 to R.string.quote_91, 92 to R.string.quote_92, 93 to R.string.quote_93,
        94 to R.string.quote_94, 95 to R.string.quote_95, 96 to R.string.quote_96,
        97 to R.string.quote_97, 98 to R.string.quote_98, 99 to R.string.quote_99,
        100 to R.string.quote_100, 101 to R.string.quote_101, 102 to R.string.quote_102,
        103 to R.string.quote_103, 104 to R.string.quote_104, 105 to R.string.quote_105,
        106 to R.string.quote_106, 107 to R.string.quote_107, 108 to R.string.quote_108,
        109 to R.string.quote_109, 110 to R.string.quote_110, 111 to R.string.quote_111,
        112 to R.string.quote_112, 113 to R.string.quote_113, 114 to R.string.quote_114,
        115 to R.string.quote_115, 116 to R.string.quote_116, 117 to R.string.quote_117,
        118 to R.string.quote_118, 119 to R.string.quote_119, 120 to R.string.quote_120,
        121 to R.string.quote_121, 122 to R.string.quote_122, 123 to R.string.quote_123,
        124 to R.string.quote_124, 125 to R.string.quote_125, 126 to R.string.quote_126,
        127 to R.string.quote_127, 128 to R.string.quote_128, 129 to R.string.quote_129,
        130 to R.string.quote_130, 131 to R.string.quote_131, 132 to R.string.quote_132,
        133 to R.string.quote_133, 134 to R.string.quote_134, 135 to R.string.quote_135,
        136 to R.string.quote_136, 137 to R.string.quote_137, 138 to R.string.quote_138,
        139 to R.string.quote_139, 140 to R.string.quote_140, 141 to R.string.quote_141,
        142 to R.string.quote_142, 143 to R.string.quote_143, 144 to R.string.quote_144,
        145 to R.string.quote_145, 146 to R.string.quote_146, 147 to R.string.quote_147,
        148 to R.string.quote_148, 149 to R.string.quote_149, 150 to R.string.quote_150,
        151 to R.string.quote_151, 152 to R.string.quote_152, 153 to R.string.quote_153,
        154 to R.string.quote_154, 155 to R.string.quote_155, 156 to R.string.quote_156,
        157 to R.string.quote_157, 158 to R.string.quote_158, 159 to R.string.quote_159,
        160 to R.string.quote_160, 161 to R.string.quote_161, 162 to R.string.quote_162,
        163 to R.string.quote_163, 164 to R.string.quote_164, 165 to R.string.quote_165,
        166 to R.string.quote_166, 167 to R.string.quote_167, 168 to R.string.quote_168,
        169 to R.string.quote_169, 170 to R.string.quote_170, 171 to R.string.quote_171,
        172 to R.string.quote_172, 173 to R.string.quote_173, 174 to R.string.quote_174,
        175 to R.string.quote_175, 176 to R.string.quote_176, 177 to R.string.quote_177,
        178 to R.string.quote_178, 179 to R.string.quote_179, 180 to R.string.quote_180,
        181 to R.string.quote_181, 182 to R.string.quote_182, 183 to R.string.quote_183,
        184 to R.string.quote_184, 185 to R.string.quote_185, 186 to R.string.quote_186,
        187 to R.string.quote_187, 188 to R.string.quote_188, 189 to R.string.quote_189,
        190 to R.string.quote_190, 191 to R.string.quote_191, 192 to R.string.quote_192,
        193 to R.string.quote_193, 194 to R.string.quote_194, 195 to R.string.quote_195,
        196 to R.string.quote_196, 197 to R.string.quote_197, 198 to R.string.quote_198,
        199 to R.string.quote_199, 200 to R.string.quote_200, 201 to R.string.quote_201,
        202 to R.string.quote_202, 203 to R.string.quote_203, 204 to R.string.quote_204,
        205 to R.string.quote_205, 206 to R.string.quote_206, 207 to R.string.quote_207,
        208 to R.string.quote_208, 209 to R.string.quote_209, 210 to R.string.quote_210,
        211 to R.string.quote_211, 212 to R.string.quote_212, 213 to R.string.quote_213,
        214 to R.string.quote_214, 215 to R.string.quote_215, 216 to R.string.quote_216,
        217 to R.string.quote_217, 218 to R.string.quote_218, 219 to R.string.quote_219,
        220 to R.string.quote_220, 221 to R.string.quote_221, 222 to R.string.quote_222,
        223 to R.string.quote_223, 224 to R.string.quote_224, 225 to R.string.quote_225,
        226 to R.string.quote_226, 227 to R.string.quote_227, 228 to R.string.quote_228,
        229 to R.string.quote_229, 230 to R.string.quote_230, 231 to R.string.quote_231,
        232 to R.string.quote_232, 233 to R.string.quote_233, 234 to R.string.quote_234,
        235 to R.string.quote_235, 236 to R.string.quote_236, 237 to R.string.quote_237,
    )

}