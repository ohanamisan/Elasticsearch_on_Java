FROM docker.elastic.co/elasticsearch/elasticsearch:6.6.1

# kuromojiをインストール
RUN elasticsearch-plugin install analysis-kuromoji

# icuをインストール
RUN elasticsearch-plugin install analysis-icu
