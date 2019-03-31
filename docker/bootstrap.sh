#!/bin/sh

docker-compose down

# create docker new image
docker build --no-cache=true -t es-java-sample:latest ./elasticsearch
docker build --no-cache=true -t es-java-sample-app:latest ./app
docker build --no-cache=true -t es-java-sample-kibana:latest ./kibana

# delete data volume
docker volume rm docker_esdata

# docker-compose restart
docker-compose up -d

# wait for elasticsearch run
sleep 60

# elasticsearch prepare
curl -XPUT "http://localhost:9200/qiita" -H 'Content-Type: application/json' -d'
{
  "settings": {
    "number_of_shards": 1,
    "analysis":{
      "analyzer":{
        "bigram_search":{
          "type":"custom",
          "tokenizer":"bigram_ja_tokenizer",
          "char_filter":["normalize","katakana"],
          "filter":["lowercase"]
        }
      },
      "char_filter": {
        "normalize": {
          "type": "icu_normalizer",
          "name": "nfkc",
          "mode": "compose"
        },
        "katakana": {
          "type": "mapping",
          "mappings": [
            "ぁ=>ァ", "ぃ=>ィ", "ぅ=>ゥ", "ぇ=>ェ", "ぉ=>ォ",
            "っ=>ッ", "ゃ=>ャ", "ゅ=>ュ", "ょ=>ョ",
            "が=>ガ", "ぎ=>ギ", "ぐ=>グ", "げ=>ゲ", "ご=>ゴ",
            "ざ=>ザ", "じ=>ジ", "ず=>ズ", "ぜ=>ゼ", "ぞ=>ゾ",
            "だ=>ダ", "ぢ=>ヂ", "づ=>ヅ", "で=>デ", "ど=>ド",
            "ば=>バ", "び=>ビ", "ぶ=>ブ", "べ=>ベ", "ぼ=>ボ",
            "ぱ=>パ", "ぴ=>ピ", "ぷ=>プ", "ぺ=>ペ", "ぽ=>ポ",
            "ヴ=>ヴ",
            "あ=>ア", "い=>イ", "う=>ウ", "え=>エ", "お=>オ",
            "か=>カ", "き=>キ", "く=>ク", "け=>ケ", "こ=>コ",
            "さ=>サ", "し=>シ", "す=>ス", "せ=>セ", "そ=>ソ",
            "た=>タ", "ち=>チ", "つ=>ツ", "て=>テ", "と=>ト",
            "な=>ナ", "に=>ニ", "ぬ=>ヌ", "ね=>ネ", "の=>ノ",
            "は=>ハ", "ひ=>ヒ", "ふ=>フ", "へ=>ヘ", "ほ=>ホ",
            "ま=>マ", "み=>ミ", "む=>ム", "め=>メ", "も=>モ",
            "や=>ヤ", "ゆ=>ユ", "よ=>ヨ",
            "ら=>ラ", "り=>リ", "る=>ル", "れ=>レ", "ろ=>ロ",
            "わ=>ワ", "を=>ヲ", "ん=>ン"
          ]
        }
      },
      "tokenizer":{
        "bigram_ja_tokenizer":{
          "type":"nGram",
          "min_gram":2,
          "max_gram":2
        }
      }
    }
  }
}
'

curl -XPUT "http://localhost:9200/qiita/_mappings/entry" -H 'Content-Type: application/json' -d'
{
  "properties": {
    "title": {
      "type": "text",
      "fields": {
        "full_text_search": {
          "type": "text",
          "search_analyzer": "bigram_search",
          "analyzer": "bigram_search"
        }
      }
    },
    "body":{
      "type": "text",
      "fields": {
        "full_text_search": {
          "type": "text",
          "search_analyzer": "bigram_search",
          "analyzer": "bigram_search"
        }
      }
    },
    "id": {
            "type": "text",
            "index": false,
            "doc_values":false
    },
    "url": {
            "type": "text",
            "index": false,
            "doc_values":false
    },
    "tags": {
            "type": "text",
            "index": false
    }
  }
}
'

curl localhost:8081/init

# delete docker none image
docker image rm $(docker image ls  --filter "dangling=true" -aq)