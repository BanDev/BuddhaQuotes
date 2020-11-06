package org.bandev.buddhaquotes.core

class Quotes {

    //Current maxQuote variable
    private var maxQuote = 237

    /**
     * Gets a quote from our quote library returns random quote if param is 0
     * @param [quoteNumber] quote number (Int)
     * @return quote as a string
     * @author jack.txt & Fennec_exe
     * @added [1008] v1.5.0 - 04/11/2020
     */

    fun find(quoteNumber: Int): String {
        val num: Int = if (quoteNumber == 0) {
            getRandomIntegerBetweenRange(0.0, maxQuote.toDouble()).toInt()
        } else {
            quoteNumber
        }
        var text = ""
        when (num) {
            1 -> text =
                "A disciplined mind brings happiness"
            2 -> text =
                "A good friend who points out mistakes and imperfections and rebukes evil is to be respected as if he reveals a secret of hidden treasure"
            3 -> text =
                "A great human revolution in just a single individual will help achieve a change in the destiny of a nation and, further, can even enable a change in the destiny of all humankind"
            4 -> text =
                "A man asked Gautama Buddha, \"I want happiness.\" Buddha said, \"First remove \"I,\" that's Ego, then remove \"want,\" that's Desire. See now you are left with only \"Happiness\""
            5 -> text =
                "A man is not called wise because he talks and talks again; but if he is peaceful, loving and fearless then he is in truth called wise"
            6 -> text =
                "A mind unruffled by the vagaries of fortune, from sorrow freed, from defilements cleansed, from fear liberated - this is the greatest blessing"
            7 -> text =
                "A person, who no matter how desperate the situation, gives others hope, is a true leader"
            8 -> text =
                "A truly compassionate attitude toward others does not change even if they behave negatively or hurt you"
            9 -> text =
                "Above all, do not lose your desire to walk"
            10 -> text =
                "'All conditioned things are impermanent' - when one sees this with wisdom, one turns away from suffering"
            11 -> text =
                "All experiences are preceded by mind, having mind as their master, created by mind"
            12 -> text =
                "All phenomena do not inherently exist because of being dependent-arisings. All phenomena do not inherently exist because of being dependently imputed"
            13 -> text =
                "All suffering is caused by ignorance. People inflict pain on others in the selfish pursuit of their own happiness or satisfaction"
            14 -> text =
                "All tremble at violence; all fear death. Putting oneself in the place of another, one should not kill nor cause another to kill"
            15 -> text =
                "All wrong-doing arises because of mind. If mind is transformed can wrong-doing remain?"
            16 -> text =
                "An open heart is an open mind"
            17 -> text =
                "Analysing through special insight and realising the lack of inherent existence constitute understanding of the signless"
            18 -> text =
                "Anger is the ultimate destroyer of your own peace of mind"
            19 -> text =
                "Anger or hatred is like a fisherman's hook. It is very important for us to ensure that we are not caught by it"
            20 -> text =
                "Anger will never disappear so long as thoughts of resentment are cherished in the mind. Anger will disappear just as soon as thoughts of resentment are forgotten"
            21 -> text =
                "Ardently do today what must be done. Who knows? Tomorrow, death comes"
            22 -> text =
                "As I am, so are these. As are these, so am I. Drawing the parallel to yourself, neither kill nor get others to kill"
            23 -> text =
                "As a flower that is lovely and beautiful, but is scentless, even so, fruitless is the well-spoken word of one who practices it not"
            24 -> text =
                "As a lotus flower is born in water, grows in water and rises out of water to stand above it unsoiled, so I, born in the world, raised in the world having overcome the world, live unsoiled by the world"
            25 -> text =
                "As a water bead on a lotus leaf, as water on a red lily, does not adhere, so the sage does not adhere to the seen, the heard, or the sensed"
            26 -> text =
                "As an elephant in the battlefield withstands arrows shot from bows all around, even so shall I endure abuse"
            27 -> text =
                "Attachment leads to suffering"
            28 -> text =
                "Attention is the rarest and purest form of generosity"
            29 -> text =
                "Be a lamp unto yourself. Work out your liberation with liberation"
            30 -> text =
                "Be greatly aware of the present"
            31 -> text =
                "Be kind whenever possible. It is always possible"
            32 -> text =
                "Be vigilant; guard your mind against negative thoughts"
            33 -> text =
                "Be what you are. This is the first step toward becoming better than you are"
            34 -> text =
                "Because we all share an identical need for love, it is possible to feel that anybody we meet, in whatever circumstances, is a brother or sister"
            35 -> text =
                "Because you are alive, everything is possible"
            36 -> text =
                "Better it is to live alone; there is no fellowship with a fool. Live alone and do no evil; be carefree like an elephant in the elephant fores"
            37 -> text =
                "Better it is to live one day seeing the rise and fall of things than to live a hundred years without ever seeing the rise and fall of things"
            38 -> text =
                "Better than a thousand useless words is one useful word, hearing which one attains peace."
            39 -> text =
                "Better than worshiping gods is obedience to the laws of righteousness"
            40 -> text =
                "By living deeply in the present moment, we can understand the past better and we can prepare for a better future"
            41 -> text =
                "Calm mind brings inner strength and self-confidence, so that's very important for good health"
            42 -> text =
                "Ceasing to do evil, Cultivating the good, Purifying the heart: This is the teaching of the Buddhas"
            43 -> text =
                "Choose to be optimistic, it feels better"
            44 -> text =
                "Conquer anger through gentleness, unkindness through kindness, greed through generosity, and falsehood by truth"
            45 -> text =
                "Conquer anger with non-anger. Conquer badness with goodness. Conquer meanness with generosity. Conquer dishonesty with truth"
            46 -> text =
                "Conquer the angry one by not getting angry; conquer the wicked by goodness; conquer the stingy by generosity, and the liar by speaking the truth"
            47 -> text =
                "Contentment is the greatest wealth"
            48 -> text =
                "Delight in heedfulness! Guard well your thoughts!"
            49 -> text =
                "Delightful are forests Where the public does not delight. There the passion-free delight, Not seeking sensual pleasure"
            50 -> text =
                "Dharma is not upheld by talking about it. Dharma is upheld by living in harmony with it..."
            51 -> text =
                "Do not give your attention to what others do or fail to do; give it to what you do or fail to do"
            52 -> text =
                "Do not overrate what you have received, nor envy others. He who envies others does not obtain peace of mind"
            53 -> text =
                "Do not think of how big the universe is, it will merely hurt your head"
            54 -> text =
                "Drop by drop is the water pot filled. Likewise, the wise man, gathering it little by little, fills himself with good"
            55 -> text =
                "Emotion arise from Desire, hence an Illusion"
            56 -> text =
                "Endurance is one of the most difficult disciplines, but it is to the one who endures that the final victory comes"
            57 -> text =
                "Even as a solid rock is unshaken by the wind, so are the wise unshaken by praise or blame"
            58 -> text =
                "Even if things don't unfold the way you expected, don't be disheartened or give up. One who continues to advance will win in the end"
            59 -> text =
                "Everything is changeable, everything appears and disappears there is no blissful peace until one passes beyond the agony of life and death"
            60 -> text =
                "Feelings come and go like clouds in a windy sky. Conscious breathing is my anchor"
            61 -> text =
                "Few among men are they who cross to the further shore. The others merely run up and down the bank on this side"
            62 -> text =
                "Following the Noble Path is like entering a dark room with a light in the hand; the darkness will all be cleared away, and the room will be filled with light"
            63 -> text =
                "For the good of the many, for the happiness of the many, out of compassion for the world"
            64 -> text =
                "Friendship is the only cure for hatred, the only guarantee of peace"
            65 -> text =
                "From a withered tree, a flower blooms"
            66 -> text =
                "Give, even if you only have a little"
            67 -> text =
                "Greater in battle than the man who would conquer a thousand-thousand men, is he who would conquer just one – himself. Better to conquer yourself than others"
            68 -> text =
                "Greater still is the truth of our connectedness"
            69 -> text =
                "Happiness mainly comes from our own attitude, rather than from external factors"
            70 -> text =
                "Hatred is never appeased by hatred in this world. By non-hatred alone is hatred appeased. This is a law eternal"
            71 -> text =
                "He has no need for faith who knows the uncreated, who has cut off rebirth, who has destroyed any opportunity for good or evil, and cast away all desire. He is indeed the ultimate man"
            72 -> text =
                "He is not the same, nor is he another"
            73 -> text =
                "He who can curb his wrath as soon as it arises, as a timely antidote will check snake's venom that so quickly spreads – such a monk gives up the here and the beyond, just as a serpent sheds its worn-out skin"
            74 -> text =
                "He who walks in the eightfold noble path with unswerving determination is sure to reach Nirvana"
            75 -> text =
                "Health is the greatest gift, contentment the greatest wealth, faithfulness the best relationship"
            76 -> text =
                "How blissful it is, for one who has nothing. Attainers-of-wisdom are people with nothing. See him suffering, one who has something, a person bound in mind with people"
            77 -> text =
                "However many holy words you read, however many you speak, what good will they do you if you do not act on upon them?"
            78 -> text =
                "I do not dispute with the world; rather it is the world that disputes with me"
            79 -> text =
                "I shall live here in the rains, There in winter, Elsewhere in summer,\" muses the fool, Not aware of the nearness of death"
            80 -> text =
                "I will not look at another’s bowl intent on finding fault: a training to be observed"
            81 -> text =
                "If a man going down into a river, swollen and swiftly flowing, is carried away by the current - how can he help others across?"
            82 -> text =
                "If a traveler does not meet with one who is his better, or his equal, let him firmly keep to his solitary journey; there is no companionship with a fool"
            83 -> text =
                "If anything is worth doing, do it with all your heart"
            84 -> text =
                "If the selflessness of phenomena is analysed and if this analysis is cultivated, it causes the effect of attaining nirvana. Through no other cause does one come to peace"
            85 -> text =
                "If we fail to look after others when they need help, who will look after us?"
            86 -> text =
                "If with a pure mind a person speaks or acts, happiness follows them like a never-departing shadow"
            87 -> text =
                "If you can cultivate the right attitude, your enemies are your best spiritual teachers because their presence provides you with the opportunity to enhance and develop your tolerance, patience and understanding"
            88 -> text =
                "If you find no one to support you on the spiritual path, walk alone There is no companionship with the immature"
            89 -> text =
                "If you knew what I know about the power of giving, you would not let a single meal pass without sharing it in some way"
            90 -> text =
                "If you love someone but rarely make yourself available to them, that is not true love"
            91 -> text =
                "If you want others to be happy, practice compassion. If you want to be happy, practice compassion"
            92 -> text =
                "In life we cannot avoid change, we cannot avoid loss. Freedom and happiness are found in the flexibility and ease with which we move through change"
            93 -> text =
                "In order to carry a positive action, we must develop here a positive vision"
            94 -> text =
                "In whom there is no sympathy for living beings: know him as an outcast"
            95 -> text =
                "It is a man's own mind, not his enemy or foe, that lures him to evil ways"
            96 -> text =
                "It is easy to see the faults of others, but difficult to see one's own faults. One shows the faults of other like chaff winnowed in the wind, but one conceals one's own faults as a cunning gambler conceals his dice"
            97 -> text =
                "It is in the nature of things that joy arises in a person free from remorse"
            98 -> text =
                "It is necessary to combine knowledge born from study with sincere practice in our daily lives. These two must go together"
            99 -> text =
                "It is not enough to be compassionate, we must act"
            100 -> text =
                "It is possible to live 24 hours in a state of love"
            101 -> text =
                "It is very important to generate a good attitude, a good heart, as much as possible. From this, happiness in both the short term and the long term for both yourself and others will come"
            102 -> text =
                "It’s your road… and yours alone… Others may walk it with you, but no one can walk it for you. No matter what path you choose, really walk it"
            103 -> text =
                "Joy comes not through possession or ownership but through a wise and loving heart"
            104 -> text =
                "Just as a solid rock is not shaken by the storm, even so the wise are not affected by praise or blame"
            105 -> text =
                "Just as the great ocean has one taste, the taste of salt, so also this teaching and discipline has one taste, the taste of liberation"
            106 -> text =
                "Know from the rivers in clefts and in crevices: those in small channels flow noisily, the great flow silent. Whatever’s not full makes noise. Whatever is full is quiet"
            107 -> text =
                "Know well what leads you forward and what holds you back, and choose the path that leads to wisdom"
            108 -> text =
                "Learn this from water: loud splashes the brook but the oceans depth are calm"
            109 -> text =
                "Leave behind the passive dreaming of a rose-tinted future. The energy of happiness exists in living today with roots sunk firmly in reality's soil"
            110 -> text =
                "Let all-embracing thoughts for all beings be yours"
            111 -> text =
                "Let him not deceive another nor despise anyone anywhere. In anger or ill will let him not wish another ill"
            112 -> text =
                "Let none find fault with others; let none see the omissions and commissions of others. But let one see one’s own acts, done and undone"
            113 -> text =
                "Letting go gives us freedom, and freedom is the only condition for happiness. If, in our heart, we still clint to anything - anger, anxiety, or possessions - we cannot be free"
            114 -> text =
                "Life is a river always flowing. Do not hold onto things. Work hard"
            115 -> text =
                "Life is available only in the present moment"
            116 -> text =
                "Like a lovely flower full of colour but lacking in fragrance, are the words of those who do not practice what they teach"
            117 -> text =
                "Like someone pointing to treasure is the wise person who sees your faults and points them out. Associate with such a sage"
            118 -> text =
                "Little by little a person becomes evil, as a water pot is filled by drops of water; Little by little a person becomes good, as a water pot is filled by drops of water"
            119 -> text =
                "Live every act fully, as if it were your last"
            120 -> text =
                "Live with no sense of ‘mine,’ not forming attachment to experiences"
            121 -> text =
                "Long is the night to him who is awake; long is a mile to him who is tired; long is life to the foolish who do not know the true law"
            122 -> text =
                "Look at situations from all angles, and you will become more open"
            123 -> text =
                "Love and compassion are necessities, not luxuries. Without them, humanity cannot survive"
            124 -> text =
                "Love is the absence of judgement"
            125 -> text =
                "Love yourself and watch - today, tomorrow, and always"
            126 -> text =
                "Many do not realise that we here must die. For those who realise this, quarrels end"
            127 -> text =
                "May all beings have happy minds"
            128 -> text =
                "Meditate… do not delay, lest you later regret it"
            129 -> text =
                "Meditate, Ānanda, do not delay, or else you will regret it later. This is our instruction to you"
            130 -> text =
                "Meditation brings wisdom; lack of meditation leaves ignorance. Know well what leads you forward and what hold you back, and choose the path that leads to wisdom"
            131 -> text =
                "Monks, even if bandits were to savagely sever you, limb by limb, with a double-handled saw, even then, whoever of you harbors ill will at heart would not be upholding my teaching"
            132 -> text =
                "More than those who hate you, more than all your enemies, an undisciplined mind does greater harm"
            133 -> text =
                "Most problems, if you give them enough time and space, will eventually wear themselves out"
            134 -> text =
                "Neither fire nor wind, birth nor death can erase our good deeds"
            135 -> text =
                "No one saves us but ourselves. No one can and no one may. We ourselves must walk the path"
            136 -> text =
                "Nothing can harm you as much as your own thoughts unguarded"
            137 -> text =
                "Nothing is forever except change"
            138 -> text =
                "One is not called noble who harms living beings. By not harming living beings one is called noble"
            139 -> text =
                "Opinion, O disciples, is a disease; opinion is a tumor; opinion is a sore. He who has overcome all opinion, O disciples, is called a saint, one who knows"
            140 -> text =
                "Our own life has to be our message"
            141 -> text =
                "Our prime purpose in this life is to help others. And if you can't help them, at least don't hurt them"
            142 -> text =
                "Over there are the roots of trees; over there, empty dwellings. Practice jhana, monks. Don’t be heedless"
            143 -> text =
                "Patience is the highest asceticism"
            144 -> text =
                "Peace does not mean an absence of conflicts; differences will always be there. Peace means solving these differences through peaceful means; through dialogue, education, knowledge; and through humane ways"
            145 -> text =
                "People have a hard time letting go of their suffering. Out of a fear of the unknown, they prefer suffering that is familiar"
            146 -> text =
                "People take different roads seeking fulfillment and happiness. Just because they're not on your road doesn't mean they've gotten lost"
            147 -> text =
                "People with opinions just go around bothering one another"
            148 -> text =
                "Perhaps the deepest reason why we are afraid of death is because we do not know who we are"
            149 -> text =
                "Praise and blame, gain and loss, pleasure and sorrow come and go like the wind. To be happy, rest like a giant tree in the midst of them all"
            150 -> text =
                "Pure-limbed, white-canopied, one-wheeled, the cart roles on. See him that cometh: faultless, stream-cutter, bondless he"
            151 -> text =
                "Purity and impurity depend on oneself; no one can purify another"
            152 -> text =
                "Radiate boundless love towards the entire world - above, below, and across - unhindered, without ill will, without enmity"
            153 -> text =
                "Real generosity is doing something nice for someone who will never find out"
            154 -> text =
                "Resolutely train yourself to attain peace"
            155 -> text =
                "See them, floundering in their sense of mind, like fish in the puddles of a dried-up stream - and, seeing this, live with no mine, not forming attachment for states of becoming"
            156 -> text =
                "Sensual craving gives rise to grief; Sensual craving gives rise to fear. For someone released from sensual craving There is no grief; And from where would be fear?"
            157 -> text =
                "Set your heart on doing good. Do it over and over again, and you will be filled with joy"
            158 -> text =
                "Should a person do good, let him do it again and again. Let him find pleasure therein, for blissful is the accumulation of good"
            159 -> text =
                "Should a seeker not find a companion who is better or equal, let them resolutely pursue a solitary course"
            160 -> text =
                "Should you find a wise critic to point out your faults, follow him as you would a guide to hidden treasure"
            161 -> text =
                "Silence is sometimes the best answer"
            162 -> text =
                "Silence the angry man with love. Silence the ill-natured man with kindness. Silence the miser with generosity. Silence the liar with truth"
            163 -> text =
                "Some do not understand that we must die, But those who do realise this settle their quarrels"
            164 -> text =
                "Sometimes your joy is the source of your smile, but sometimes your smile can be the source of your joy"
            165 -> text =
                "Speak only endearing speech, speech that is welcomed. Speech, when it brings no evil to others, is a pleasant thing"
            166 -> text =
                "Speak the truth, do not become angered, and give when asked, even be it a little. By these three conditions, one goes to the presence of the gods"
            167 -> text =
                "The calmed say that what is well-spoken is best; second, that one should say what is right, not unrighteous; third, what’s pleasing, not displeasing; fourth, what is true, not false"
            168 -> text =
                "The darkest night is ignorance"
            169 -> text =
                "The Gift of Truth excels all other Gifts"
            170 -> text =
                "The growth of wisdom may be measured exactly by the diminution of ill temper"
            171 -> text =
                "The ignorant man is an ox. He grows in size, not in wisdom"
            172 -> text =
                "The less you have, the less you have to worry about"
            173 -> text =
                "The moment you see how important it is to love yourself, you will stop making others suffer"
            174 -> text =
                "The more you are motivated by Love, The more Fearless and Free your action will be"
            175 -> text =
                "The non-doing of any evil, the performance of what's skillful, the cleansing of one's own mind: this is the teaching of the Awakened"
            176 -> text =
                "The one in whom no longer exist the craving and thirst that perpetuate becoming; how could you track that Awakened one, trackless, and of limitless range"
            177 -> text =
                "The one who has conquered himself is a far greater hero than he who has defeated a thousand times a thousand men"
            178 -> text =
                "The rain could turn to gold and still your thirst would not be slaked. Desire is unquenchable or it ends in tears, even in heaven"
            179 -> text =
                "The root of suffering is attachment"
            180 -> text =
                "The roots of all goodness lie in the soil of appreciation for goodness"
            181 -> text =
                "The source of love is deep in us and we can help others realise a lot of happiness. One word, one action, one thought can reduce another person's suffering and bring that person joy"
            182 -> text =
                "The universe itself is change and life itself is but what you deem it"
            183 -> text =
                "The virtuous man delights in this world and he delights in the next"
            184 -> text =
                "The way to change others' minds is with affection, and not anger"
            185 -> text =
                "The wise man makes an island of himself that no flood can overwhelm"
            186 -> text =
                "The world is afflicted by death and decay. But the wise do not grieve, having realised the nature of the world"
            187 -> text =
                "There are no chains like hate… Dwelling on your brother’s faults multiplies your own. You are far from the end of your journey"
            188 -> text =
                "There is no fear for one whose mind is not filled with desires"
            189 -> text =
                "There is no fire like passion, there is no shark like hatred, there is no snare like folly, there is no torrent like greed"
            190 -> text =
                "There is nothing so disobedient as an undisciplined mind, and there is nothing so obedient as a disciplined mind"
            191 -> text =
                "They blame those who remain silent, they blame those who speak much, they blame those who speak in moderation. There is none in the world who is not blamed"
            192 -> text =
                "Think not lightly of evil, saying 'It will not come to me.' Drop by drop is the water pot filled. Likewise the fool, gathering it little by little, fills himself with evil"
            193 -> text =
                "Those which are produced from causes are not produced. They do not have an inherent nature of production. Those which depend on causes are said to be empty; those who know emptiness are aware"
            194 -> text =
                "Those which arise dependently are free of inherent existence"
            195 -> text =
                "Those who are free of resentful thoughts surely find peace"
            196 -> text =
                "Those who cling to perceptions and views wander the world offending people"
            197 -> text =
                "Those who have failed to work toward the truth have missed the purpose of living"
            198 -> text =
                "Those who really seek the path to Enlightenment dictate terms to their mind. Then they proceed with strong determination"
            199 -> text =
                "Though one should live a hundred years without wisdom and control, yet better, indeed, is a single day’s life of one who is wise and meditative"
            200 -> text =
                "Thousands of candles can be lit from a single candle, and the life of the candle will not be shortened. Happiness never decreases by being shared"
            201 -> text =
                "Three things cannot be long hidden: the sun, the moon, and the truth"
            202 -> text =
                "Times of luxury do not last long, but pass away very quickly nothing in this world can be long enjoyed"
            203 -> text =
                "To be beautiful means to be yourself. You don't need to be accepted by others. You need to accept yourself"
            204 -> text =
                "To become vegetarian is to step into the stream which leads to nirvana"
            205 -> text =
                "To insist on a spiritual practice that served you in the past is to carry the raft on your back after you have crossed the river"
            206 -> text =
                "To keep the body in good health is a duty... otherwise we shall not be able to keep our mind strong and clear"
            207 -> text =
                "To live in the consciousness of the inevitability of suffering, of becoming enfeebled, of old age and of death, is impossible – we must free ourselves from life, from all possible life"
            208 -> text =
                "To refrain from evil and from strong drink and to be always steadfast in virtue; this is the good luck"
            209 -> text =
                "To support mother and father, to cherish wife and children, and to be engaged in peaceful occupation - this is the greatest blessing"
            210 -> text =
                "True change is within, leave the outside as it is"
            211 -> text =
                "Understanding is the heartwood of well-spoken words"
            212 -> text =
                "Victory breeds hatred. The defeated live in pain. Happily the peaceful live, giving up victory and defeat"
            213 -> text =
                "Virtuous people always let go. They don't prattle about pleasures and desires. Touched by happiness and then by suffering, The sage shows no sign of being elated or depressed"
            214 -> text =
                "We are here to awaken from our illusion of separateness"
            215 -> text =
                "We will develop love, we will practice it, we will make it both a way and a basis…"
            216 -> text =
                "We will develop and cultivate the liberation of mind by lovingkindness, make it our vehicle, make it our basis, stabilize it, exercise ourselves in it, and fully perfect it"
            217 -> text =
                "Whatever a monk keeps pursuing with his thinking and pondering, that becomes the inclination of his awareness"
            218 -> text =
                "Whatever an enemy might do to an enemy, or a foe to a foe, the ill-directed mind can do to you even worse"
            219 -> text =
                "Whatever has the nature of arising has the nature of ceasing"
            220 -> text =
                "Whatever is not yours: let go of it. Your letting go of it will be for your long-term happiness & benefit"
            221 -> text =
                "Whatever precious jewel there is in the heavenly worlds, there is nothing comparable to one who is Awakened"
            222 -> text =
                "When one has the feeling of dislike for evil, when one feels tranquil, one finds pleasure in listening to good teachings; when one has these feelings and appreciates them, one is free of fear"
            223 -> text =
                "When the Aggregates arise, decay and die, O bhikkhu, every moment you are born, decay, and die"
            224 -> text =
                "When watching after yourself, you watch after others. When watching after others, you watch after yourself"
            225 -> text =
                "When we feel love and kindness toward others, it not only makes others feel loved and cared for, but it helps us also to develop inner happiness and peace"
            226 -> text =
                "When you come upon a path that brings benefit and happiness to all, follow this course as the moon journeys through the stars"
            227 -> text =
                "When you practice gratefulness, there is a sense of respect toward others"
            228 -> text =
                "Who leaves behind all human bonds And has cast off the bonds of heaven, Detached from all bonds everywhere: He is the one I call a brahmin"
            229 -> text =
                "Whoever doesn’t flare up at someone who’s angry wins a battle hard to win\n"
            230 -> text =
                "Whoever sees me sees the teaching, and whoever sees the teaching sees me"
            231 -> text =
                "With fools, there is no companionship. Rather than to live with men who are selfish, vain, quarrelsome, and obstinate, let a man walk alone"
            232 -> text =
                "With realisation of one's own potential and self-confidence, one can build a better world"
            233 -> text =
                "Without health life is not life; it is only a state of langour and suffering - an image of death"
            234 -> text =
                "You are the community now. Be a lamp for yourselves. Be your own refuge. Seek for no other. All things must pass. Strive on diligently. Don’t give up"
            235 -> text =
                "You don't gather the beauty of a flower by plucking her petals"
            236 -> text =
                "Your own self is your master; who else could be? With yourself well controlled, you gain a master very hard to find"
            237 -> text =
                "You yourself must strive. The Buddhas only point the way"
        }
        return text
    }

    private fun getRandomIntegerBetweenRange(min: Double, max: Double): Double {
        return (Math.random() * (max - min + 1)).toInt() + min
    }
}