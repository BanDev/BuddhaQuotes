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
 */

class Quotes {

    private var quoteIdGlobal: Int = 0
    private val lastQuoteId = 237
    private val allQuoteIds = 1..lastQuoteId

    /**
     * Returns the quote with the corresponding id (0 for a random quote)
     *
     * @param [id] is the id of the requested quote (Int)
     * @param [context] context of activity (Context)
     * @return the quote (String)
     */
    fun getQuote(id: Int, context: Context): String {
        quoteIdGlobal = if (id in allQuoteIds) id else (allQuoteIds).random()

        val quoteResource = when (quoteIdGlobal) {
            1 -> R.string.quote_1
            2 -> R.string.quote_2
            3 -> R.string.quote_3
            4 -> R.string.quote_4
            5 -> R.string.quote_5
            6 -> R.string.quote_6
            7 -> R.string.quote_7
            8 -> R.string.quote_8
            9 -> R.string.quote_9
            10 -> R.string.quote_10
            11 -> R.string.quote_11
            12 -> R.string.quote_12
            13 -> R.string.quote_13
            14 -> R.string.quote_14
            15 -> R.string.quote_15
            16 -> R.string.quote_16
            17 -> R.string.quote_17
            18 -> R.string.quote_18
            19 -> R.string.quote_19
            20 -> R.string.quote_20
            21 -> R.string.quote_21
            22 -> R.string.quote_22
            23 -> R.string.quote_23
            24 -> R.string.quote_24
            25 -> R.string.quote_25
            26 -> R.string.quote_26
            27 -> R.string.quote_27
            28 -> R.string.quote_28
            29 -> R.string.quote_29
            30 -> R.string.quote_30
            31 -> R.string.quote_31
            32 -> R.string.quote_32
            33 -> R.string.quote_33
            34 -> R.string.quote_34
            35 -> R.string.quote_35
            36 -> R.string.quote_36
            37 -> R.string.quote_37
            38 -> R.string.quote_38
            39 -> R.string.quote_39
            40 -> R.string.quote_40
            41 -> R.string.quote_41
            42 -> R.string.quote_42
            43 -> R.string.quote_43
            44 -> R.string.quote_44
            45 -> R.string.quote_45
            46 -> R.string.quote_46
            47 -> R.string.quote_47
            48 -> R.string.quote_48
            49 -> R.string.quote_49
            50 -> R.string.quote_50
            51 -> R.string.quote_51
            52 -> R.string.quote_52
            53 -> R.string.quote_53
            54 -> R.string.quote_54
            55 -> R.string.quote_55
            56 -> R.string.quote_56
            57 -> R.string.quote_57
            58 -> R.string.quote_58
            59 -> R.string.quote_59
            60 -> R.string.quote_60
            61 -> R.string.quote_61
            62 -> R.string.quote_62
            63 -> R.string.quote_63
            64 -> R.string.quote_64
            65 -> R.string.quote_65
            66 -> R.string.quote_66
            67 -> R.string.quote_67
            68 -> R.string.quote_68
            69 -> R.string.quote_69
            70 -> R.string.quote_70
            71 -> R.string.quote_71
            72 -> R.string.quote_72
            73 -> R.string.quote_73
            74 -> R.string.quote_74
            75 -> R.string.quote_75
            76 -> R.string.quote_76
            77 -> R.string.quote_77
            78 -> R.string.quote_78
            79 -> R.string.quote_79
            80 -> R.string.quote_80
            81 -> R.string.quote_81
            82 -> R.string.quote_82
            83 -> R.string.quote_83
            84 -> R.string.quote_84
            85 -> R.string.quote_85
            86 -> R.string.quote_86
            87 -> R.string.quote_87
            88 -> R.string.quote_88
            89 -> R.string.quote_89
            90 -> R.string.quote_90
            91 -> R.string.quote_91
            92 -> R.string.quote_92
            93 -> R.string.quote_93
            94 -> R.string.quote_94
            95 -> R.string.quote_95
            96 -> R.string.quote_96
            97 -> R.string.quote_97
            98 -> R.string.quote_98
            99 -> R.string.quote_99
            100 -> R.string.quote_100
            101 -> R.string.quote_101
            102 -> R.string.quote_102
            103 -> R.string.quote_103
            104 -> R.string.quote_104
            105 -> R.string.quote_105
            106 -> R.string.quote_106
            107 -> R.string.quote_107
            108 -> R.string.quote_108
            109 -> R.string.quote_109
            110 -> R.string.quote_110
            111 -> R.string.quote_111
            112 -> R.string.quote_112
            113 -> R.string.quote_113
            114 -> R.string.quote_114
            115 -> R.string.quote_115
            116 -> R.string.quote_116
            117 -> R.string.quote_117
            118 -> R.string.quote_118
            119 -> R.string.quote_119
            120 -> R.string.quote_120
            121 -> R.string.quote_121
            122 -> R.string.quote_122
            123 -> R.string.quote_123
            124 -> R.string.quote_124
            125 -> R.string.quote_125
            126 -> R.string.quote_126
            127 -> R.string.quote_127
            128 -> R.string.quote_128
            129 -> R.string.quote_129
            130 -> R.string.quote_130
            131 -> R.string.quote_131
            132 -> R.string.quote_132
            133 -> R.string.quote_133
            134 -> R.string.quote_134
            135 -> R.string.quote_135
            136 -> R.string.quote_136
            137 -> R.string.quote_137
            138 -> R.string.quote_138
            139 -> R.string.quote_139
            140 -> R.string.quote_140
            141 -> R.string.quote_141
            142 -> R.string.quote_142
            143 -> R.string.quote_143
            144 -> R.string.quote_144
            145 -> R.string.quote_145
            146 -> R.string.quote_146
            147 -> R.string.quote_147
            148 -> R.string.quote_148
            149 -> R.string.quote_149
            150 -> R.string.quote_150
            151 -> R.string.quote_151
            152 -> R.string.quote_152
            153 -> R.string.quote_153
            154 -> R.string.quote_154
            155 -> R.string.quote_155
            156 -> R.string.quote_156
            157 -> R.string.quote_157
            158 -> R.string.quote_158
            159 -> R.string.quote_159
            160 -> R.string.quote_160
            161 -> R.string.quote_161
            162 -> R.string.quote_162
            163 -> R.string.quote_163
            164 -> R.string.quote_164
            165 -> R.string.quote_165
            166 -> R.string.quote_166
            167 -> R.string.quote_167
            168 -> R.string.quote_168
            169 -> R.string.quote_169
            170 -> R.string.quote_170
            171 -> R.string.quote_171
            172 -> R.string.quote_172
            173 -> R.string.quote_173
            174 -> R.string.quote_174
            175 -> R.string.quote_175
            176 -> R.string.quote_176
            177 -> R.string.quote_177
            178 -> R.string.quote_178
            179 -> R.string.quote_179
            180 -> R.string.quote_180
            181 -> R.string.quote_181
            182 -> R.string.quote_182
            183 -> R.string.quote_183
            184 -> R.string.quote_184
            185 -> R.string.quote_185
            186 -> R.string.quote_186
            187 -> R.string.quote_187
            188 -> R.string.quote_188
            189 -> R.string.quote_189
            190 -> R.string.quote_190
            191 -> R.string.quote_191
            192 -> R.string.quote_192
            193 -> R.string.quote_193
            194 -> R.string.quote_194
            195 -> R.string.quote_195
            196 -> R.string.quote_196
            197 -> R.string.quote_197
            198 -> R.string.quote_198
            199 -> R.string.quote_199
            200 -> R.string.quote_200
            201 -> R.string.quote_201
            202 -> R.string.quote_202
            203 -> R.string.quote_203
            204 -> R.string.quote_204
            205 -> R.string.quote_205
            206 -> R.string.quote_206
            207 -> R.string.quote_207
            208 -> R.string.quote_208
            209 -> R.string.quote_209
            210 -> R.string.quote_210
            211 -> R.string.quote_211
            212 -> R.string.quote_212
            213 -> R.string.quote_213
            214 -> R.string.quote_214
            215 -> R.string.quote_215
            216 -> R.string.quote_216
            217 -> R.string.quote_217
            218 -> R.string.quote_218
            219 -> R.string.quote_219
            220 -> R.string.quote_220
            221 -> R.string.quote_221
            222 -> R.string.quote_222
            223 -> R.string.quote_223
            224 -> R.string.quote_224
            225 -> R.string.quote_225
            226 -> R.string.quote_226
            227 -> R.string.quote_227
            228 -> R.string.quote_228
            229 -> R.string.quote_229
            230 -> R.string.quote_230
            231 -> R.string.quote_231
            232 -> R.string.quote_232
            233 -> R.string.quote_233
            234 -> R.string.quote_234
            235 -> R.string.quote_235
            236 -> R.string.quote_236
            else -> R.string.quote_237
        }

        return context.getString(quoteResource)
    }

}
