package org.bandev.buddhaquotes.architecture.quotes

import org.bandev.buddhaquotes.R

object QuoteStore {
    val quotesWithSources = mapOf(
        R.string.as_i_am_so_are_they_quote to QuoteSource(R.string.from_sutta_nipata),
        R.string.a_disciplined_mind_brings_happiness_quote to QuoteSource(
            bodyRes = R.string.dhammapada_verse,
            verse = 35,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.03.budd.html"
        ),
        R.string.a_noble_one_produces_quote to QuoteSource(
            bodyRes = R.string.itivuttika_source,
            fullQuoteRes = R.string.quote_3_full_quote,
            url = "https://suttacentral.net/iti27/ireland"
        ),
        R.string.a_wise_person_should_quote to QuoteSource(
            bodyRes = R.string.itivuttika_source,
            url = "https://suttacentral.net/iti27/ireland"
        ),
        R.string.all_experiences_are_preceded_quote to QuoteSource(
            bodyRes = R.string.dhammapada_source,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.01.budd.html#dhp-1"
        ),
        R.string.quote_6 to QuoteSource(R.string.from_bhaddekaratta_sutta),
        R.string.quote_7 to QuoteSource(
            bodyRes = R.string.dhammapada_verse,
            verse = 223,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.17.budd.html#dhp-223"
        ),
        R.string.delight_in_heedfulness_quote to QuoteSource(
            bodyRes = R.string.dhammapada_source,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.23.budd.html#dhp-327"
        ),
        R.string.quote_9 to QuoteSource(
            bodyRes = R.string.dhammapada_verse,
            verse = 224,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.17.budd.html#dhp-224"
        ),
        R.string.quote_10 to QuoteSource(
            bodyRes = R.string.from_samyutta_nikaya,
            url = "https://suttacentral.net/en/sn22.94"
        ),
        R.string.quote_11 to QuoteSource(R.string.dhammapada_source),
        R.string.quote_12 to QuoteSource(R.string.from_sutta_nipata),
        R.string.quote_13 to QuoteSource(
            bodyRes = R.string.from_cetana_sutta_of_anguttara_nikaya,
            url = "https://www.accesstoinsight.org/tipitaka/an/an11/an11.002.than.html"
        ),
        R.string.quote_14 to QuoteSource(
            bodyRes = R.string.from_sallekha_sutta,
            url = "https://www.accesstoinsight.org/tipitaka/mn/mn.008.nypo.html"
        ),
        R.string.quote_15 to QuoteSource(
            bodyRes = R.string.from_sallekha_sutta,
            url = "http://www.accesstoinsight.org/tipitaka/mn/mn.008.nypo.html"
        ),
        R.string.quote_16 to QuoteSource(
            bodyRes = R.string.itivuttika_source,
            url = "https://suttacentral.net/iti22/ireland"
        ),
        R.string.quote_17 to QuoteSource(
            bodyRes = R.string.dhammapada_source,
            fullQuoteRes = R.string.quote_17_full_quote,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.intro.than.html#fn-165"
        ),
        R.string.quote_18 to QuoteSource(
            bodyRes = R.string.from_karaniya_metta_sutta,
            fullQuoteRes = R.string.quote_61,
            url = "https://www.accesstoinsight.org/tipitaka/kn/snp/snp.1.08.piya.html"
        ),
        R.string.quote_19 to QuoteSource(
            bodyRes = R.string.from_sutta_nipata,
            url = "https://www.accesstoinsight.org/tipitaka/kn/snp/snp.4.02.than.html"
        ),
        R.string.quote_20 to QuoteSource(
            bodyRes = R.string.dhammapada_verse,
            verse = 61,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.05.budd.html#dhp-61"
        ),
        R.string.quote_21 to QuoteSource(
            bodyRes = R.string.dhammapada_verse,
            verse = 76,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.06.budd.html#dhp-76"
        ),
        R.string.quote_22 to QuoteSource(
            bodyRes = R.string.from_sutta_nipata,
            url = "https://www.accesstoinsight.org/tipitaka/kn/snp/snp.3.03.than.html"
        ),
        R.string.quote_23 to QuoteSource(
            bodyRes = R.string.dhammapada_verse,
            verse = 180,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.14.budd.html#dhp-180"
        ),
        R.string.quote_24 to QuoteSource(
            bodyRes = R.string.dhammapada_verse,
            verse = 39,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.03.budd.html"
        ),
        R.string.quote_25 to QuoteSource(R.string.from_mangala_sutta),
        R.string.quote_26 to QuoteSource(R.string.from_kimsika_sutta),
        R.string.quote_27 to QuoteSource(R.string.quote_27_source),
        R.string.quote_28 to QuoteSource(
            bodyRes = R.string.from_na_sumhaka_sutta_of_samyutta_nikaya,
            url = "https://www.accesstoinsight.org/tipitaka/sn/sn35/sn35.101.than.html"
        ),
        R.string.quote_29 to QuoteSource(
            bodyRes = R.string.from_sutta_nipata,
            url = "https://www.accesstoinsight.org/tipitaka/kn/snp/snp.2.01.piya.html"
        ),
        R.string.quote_30 to QuoteSource(
            bodyRes = R.string.dhammapada_verse,
            verse = 100,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.08.budd.html"
        ),
        R.string.quote_31 to QuoteSource(
            bodyRes = R.string.dhammapada_verse,
            verse = 331,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.23.budd.html"
        ),
        R.string.quote_32 to QuoteSource(
            bodyRes = R.string.dhammapada_verse,
            verse = 50,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.04.budd.html"
        ),
        R.string.quote_33 to QuoteSource(
            bodyRes = R.string.dhammapada_verse,
            verse = 118,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.09.budd.html"
        ),
        R.string.quote_34 to QuoteSource(
            bodyRes = R.string.from_samyutta_nikaya_verse,
            verse = 44.1
        ),
        R.string.quote_35 to QuoteSource(
            bodyRes = R.string.from_samyutta_nikaya_verse,
            verse = 55.7
        ),
        R.string.quote_36 to QuoteSource(
            bodyRes = R.string.dhammapada_verse,
            verse = 277,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.20.budd.html"
        ),
        R.string.quote_37 to QuoteSource(
            bodyRes = R.string.from_nalaka_sutta_of_sutta_nipata,
            url = "https://www.accesstoinsight.org/tipitaka/kn/snp/snp.3.11.than.html"
        ),
        R.string.quote_38 to QuoteSource(R.string.from_mangala_sutta),
        R.string.quote_39 to QuoteSource(
            bodyRes = R.string.dhammapada_verse,
            verse = 129,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.10.budd.html"
        ),
        R.string.quote_40 to QuoteSource(
            bodyRes = R.string.from_jara_sutta_of_sutta_nipata,
            fullQuoteRes = R.string.quote_40_full_quote,
            url = "https://www.accesstoinsight.org/tipitaka/kn/snp/snp.4.06.than.html"
        ),
        R.string.quote_41 to QuoteSource(
            bodyRes = R.string.dhammapada_verse,
            verse = 320,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.23.budd.html"
        ),
        R.string.quote_42 to QuoteSource(
            bodyRes = R.string.dhammapada_source,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.08.budd.html#dhp-113"
        ),
        R.string.quote_43 to QuoteSource(
            bodyRes = R.string.from_sutta_nipata,
            url = "https://www.accesstoinsight.org/tipitaka/sn/sn22/sn22.086.than.html"
        ),
        R.string.quote_44 to QuoteSource(
            bodyRes = R.string.dhammapada_verse,
            verse = 183,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.14.budd.html#dhp-183"
        ),
        R.string.quote_45 to QuoteSource(
            bodyRes = R.string.dhammapada_source,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.09.budd.html#dhp-122"
        ),
        R.string.quote_46 to QuoteSource(
            bodyRes = R.string.dhammapada_source,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.01.budd.html#dhp-5"
        ),
        R.string.quote_47 to QuoteSource(
            bodyRes = R.string.from_nalaka_sutta_of_sutta_nipata,
            url = "https://www.accesstoinsight.org/tipitaka/kn/snp/snp.3.11.than.html"
        ),
        R.string.quote_48 to QuoteSource(
            bodyRes = R.string.from_sutta_nipata,
            url = "https://www.accesstoinsight.org/tipitaka/kn/snp/snp.1.01.nypo.html"
        ),
        R.string.quote_49 to QuoteSource(
            bodyRes = R.string.from_vinaya,
            url = "https://www.accesstoinsight.org/lib/authors/thanissaro/bmc1/bmc1.ch10.html#Sk35"
        ),
        R.string.quote_50 to QuoteSource(
            bodyRes = R.string.from_sutta_nipata,
            url = "https://www.accesstoinsight.org/tipitaka/kn/snp/snp.2.08.irel.html"
        ),
        R.string.quote_51 to QuoteSource(
            bodyRes = R.string.from_mahaparinibbana_sutta,
            url = "https://www.accesstoinsight.org/tipitaka/dn/dn.16.1-6.vaji.html#ref4"
        ),
        R.string.quote_52 to QuoteSource(
            bodyRes = R.string.dhammapada_verse,
            verse = 80,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.06.budd.html"
        ),
        R.string.quote_53 to QuoteSource(
            bodyRes = R.string.dhammapada_verse,
            verse = 81,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.06.budd.html#dhp-81"
        ),
        R.string.quote_54 to QuoteSource(
            bodyRes = R.string.from_udana,
            url = "https://www.accesstoinsight.org/tipitaka/kn/ud/ud.5.05.irel.html"
        ),
        R.string.quote_55 to QuoteSource(
            bodyRes = R.string.from_sutta_nipata,
            url = "https://www.accesstoinsight.org/tipitaka/kn/snp/snp.3.11.than.html"
        ),
        R.string.quote_56 to QuoteSource(
            bodyRes = R.string.from_metta_sutta,
            url = "https://www.accesstoinsight.org/tipitaka/kn/snp/snp.1.08.budd.html"
        ),
        R.string.quote_57 to QuoteSource(
            bodyRes = R.string.dhammapada_verse,
            verse = 50,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.04.budd.html#dhp-50"
        ),
        R.string.quote_58 to QuoteSource(
            bodyRes = R.string.from_karaniya_metta_sutta,
            fullQuoteRes = R.string.quote_58_full_quote
        ),
        R.string.quote_59 to QuoteSource(
            bodyRes = R.string.dhammapada_verse,
            verse = 270,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.19.budd.html"
        ),
        R.string.quote_60 to QuoteSource(
            bodyRes = R.string.from_kamma_sutta,
            fullQuoteRes = R.string.quote_60_full_quote,
            url = "https://www.accesstoinsight.org/tipitaka/sn/sn35/sn35.145.than.html"
        ),
        R.string.quote_61 to QuoteSource(
            bodyRes = R.string.from_metta_sutta,
            url = "https://www.accesstoinsight.org/tipitaka/kn/snp/snp.1.08.piya.html"
        ),
        R.string.quote_62 to QuoteSource(
            bodyRes = R.string.from_utthana_sutta_of_sutta_nipata,
            url = "https://www.accesstoinsight.org/tipitaka/kn/snp/snp.2.10.irel.html"
        ),
        R.string.quote_63 to QuoteSource(
            bodyRes = R.string.dhammapada_verse,
            verse = 118,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.09.budd.html#dhp-118"
        ),
        R.string.quote_64 to QuoteSource(
            bodyRes = R.string.dhammapada_verse,
            verse = 6,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.01.budd.html"
        ),
        R.string.quote_65 to QuoteSource(
            bodyRes = R.string.from_sutta_nipata,
            url = "https://www.accesstoinsight.org/tipitaka/kn/snp/snp.3.03.than.html"
        ),
        R.string.quote_66 to QuoteSource(
            bodyRes = R.string.quote_66_source,
            url = "https://www.accesstoinsight.org/tipitaka/mn/mn.105.than.html"
        ),
        R.string.quote_67 to QuoteSource(
            bodyRes = R.string.from_sutta_nipata,
            url = "https://www.accesstoinsight.org/tipitaka/kn/snp/snp.3.08.irel.html"
        ),
        R.string.quote_68 to QuoteSource(
            bodyRes = R.string.dhammapada_verse,
            verse = 227,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.17.budd.html"
        ),
        R.string.quote_69 to QuoteSource(R.string.from_magandiya_suta_of_sutta_nipata),
        R.string.quote_70 to QuoteSource(
            bodyRes = R.string.from_mangala_sutta,
            url = "https://www.accesstoinsight.org/tipitaka/kn/snp/snp.2.04.nara.html"
        ),
        R.string.quote_71 to QuoteSource(
            bodyRes = R.string.from_samyutta_nikaya,
            fullQuoteRes = R.string.quote_71_full_quote
        ),
        R.string.quote_72 to QuoteSource(
            bodyRes = R.string.from_samyutta_nikaya,
            url = "https://www.accesstoinsight.org/tipitaka/sn/sn47/sn47.019.than.html"
        ),
        R.string.quote_73 to QuoteSource(
            bodyRes = R.string.from_samyutta_nikaya,
            url = "https://www.accesstoinsight.org/tipitaka/sn/sn07/sn07.002.than.html"
        ),
        R.string.quote_74 to QuoteSource(
            bodyRes = R.string.dhammapada_verse,
            verse = 276,
            fullQuoteRes = R.string.quote_74_full_quote,
            url = "https://www.accesstoinsight.org/tipitaka/kn/dhp/dhp.20.budd.html#dhp-276"
        ),
        R.string.quote_75 to QuoteSource(
            bodyRes = R.string.good_friends_companions_source,
            fullQuoteRes = R.string.good_friends_companions_full_quote,
            url = "https://suttacentral.net/sn45.2/en/bodhi"
        ),
        R.string.quote_76 to null,
        R.string.quote_77 to null,
        R.string.quote_78 to null,
        R.string.quote_79 to null,
        R.string.quote_80 to null,
        R.string.quote_81 to null,
        R.string.quote_82 to null,
        R.string.quote_83 to null,
        R.string.quote_84 to null,
        R.string.quote_85 to null,
    )
}
