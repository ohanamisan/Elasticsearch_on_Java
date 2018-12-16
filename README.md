# Elasticsearch_on_Java

ElasticsearchのJavaClientを使った全文検索のサンプルアプリです。
アプリ起動前に接続先のElasticsearchを起動と以下index, analyzer, mappingの
設定をしてください。

### Gradle
またmavenからはelasticsearch.coreに加えてrestHighLevelClientも引っ張てきます。
coreのclientパッケージには入っていません。
Elasticsearchのバージョンは6.5.2を使用しています。
ご使用のバージョンに合わせてGradle内のバージョンを変更してください。

### plugin
ICU Analysis
```
elasticsearch-plugin install analysis-icu
```

### analyzer, mapping
日本語辞書関連のプラグインは使用せず、
bigramでアナライズし、全文検索をかけていきます。

index, analyzer, mappingの詳細は[ elasticsearch ]パッケージ内にテキストファイルがあります。
KibanaのDevtoolsで流すか、加工してcurlで流してください。

### Data
実行時にQiitaのAPIを叩いて記事500件がElasticsearchに自動的に入ります。
