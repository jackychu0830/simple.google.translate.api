# Simple Google Translate API
Very simple Google Translator API Java version. No need apply API code.
And response is very simple JSON text. Easy to read and parse.

## Build
mvn package

## Run (from jar file)
```shell
java -jar target/simple.google.translate.api-1.0-SNAPSHOT-jar-with-dependencies.jar
```
### Result Example
```
Please type the text which you want to translate: Hello, World!
1: Afrikaans (af)
2: Albanian (sq)
3: Amharic (am)
4: Arabic (ar)
5: Armenian (hy)
6: Azerbaijani (az)
7: Basque (eu)
8: Belarusian (be)
9: Bengali (bn)
10: Bosnian (bs)
11: Bulgarian (bg)
12: Catalan (ca)
13: Cebuano (ceb)
14: Chichewa (ny)
15: Chinese Simplified (zh_cn)
16: Chinese Traditional (zh_tw)
17: Corsican (co)
18: Croatian (hr)
19: Czech (cs)
20: Danish (da)
21: Dutch (nl)
22: English (en)
23: Esperanto (eo)
24: Estonian (et)
25: Filipino (tl)
26: Finnish (fi)
27: French (fr)
28: Frisian (fy)
29: Galician (gl)
30: Georgian (ka)
31: German (de)
32: Greek (el)
33: Gujarati (gu)
34: Haitian Creole (ht)
35: Hausa (ha)
36: Hawaiian (haw)
37: Hebrew (iw)
38: Hindi (hi)
39: Hmong (hmn)
40: Hungarian (hu)
41: Icelandic (is)
42: Igbo (ig)
43: Indonesian (id)
44: Irish (ga)
45: Italian (it)
46: Japanese (ja)
47: Javanese (jw)
48: Kannada (kn)
49: Kazakh (kk)
50: Khmer (km)
51: Korean (ko)
52: Kurdish (Kurmanji) (ku)
53: Kyrgyz (ky)
54: Lao (lo)
55: Latin (la)
56: Latvian (lv)
57: Lithuanian (lt)
58: Luxembourgish (lb)
59: Macedonian (mk)
60: Malagasy (mg)
61: Malay (ms)
62: Malayalam (ml)
63: Maltese (mt)
64: Maori (mi)
65: Marathi (mr)
66: Mongolian (mn)
67: Myanmar (Burmese) (my)
68: Nepali (ne)
69: Norwegian (no)
70: Pashto (ps)
71: Persian (fa)
72: Polish (pl)
73: Portuguese (pt)
74: Punjabi (ma)
75: Romanian (ro)
76: Russian (ru)
77: Samoan (sm)
78: Scots Gaelic (gd)
79: Serbian (sr)
80: Sesotho (st)
81: Shona (sn)
82: Sindhi (sd)
83: Sinhala (si)
84: Slovak (sk)
85: Slovenian (sl)
86: Somali (so)
87: Spanish (es)
88: Sundanese (su)
89: Swahili (sw)
90: Swedish (sv)
91: Tajik (tg)
92: Tamil (ta)
93: Telugu (te)
94: Thai (th)
95: Turkish (tr)
96: Ukrainian (uk)
97: Urdu (ur)
98: Uzbek (uz)
99: Vietnamese (vi)
100: Welsh (cy)
101: Xhosa (xh)
102: Yiddish (yi)
103: Yoruba (yo)
104: Zulu (zu)

Please select the language which you want translate to: 16
Translating to Chinese Traditional
Result:
你好，世界！ 
```

## Use API
```
import com.jackychu.api.simplegoogletranslate.SimpleGoogleTranslate;

SimpleGoogleTranslate translate = new SimpleGoogleTranslate();        
String result = translate.doTranslate(Language.auto, Language.zh_tw, "Hello, World!"));
```

# How does this api work?
### URL:
https://translate.googleapis.com/translate_a/single

### Parameters:
* client=gtx (don't change)
* dt=t (don't change)
* sl=zh_cn (Source language, for example zh-CN for Chinese Simplified)
* tl=zh_tw (Target language, for example zh-TW for Chinese Traditional)
* q=(query text. What you want to translate. URL encoded)

### Example:

https://translate.googleapis.com/translate_a/single?client=gtx&sl=zh_cn&tl=zh_tw&&dt=t&q=记得按下订阅按钮

### Response (JSON format):
```json
[
[
[[
  [
    [
      "記得按下訂閱按鈕",
      "记得按下订阅按钮",
      null,
      null,
      0
    ]
  ],
  null,
  "zh-CN",
  null,
  null,
  null,
  null,
  []
]]
]
]
```

## Reference
* https://www.jianshu.com/p/29f95efaa88b
* https://www.796t.com/article.php?id=175636
