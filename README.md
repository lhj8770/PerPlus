# Perplus
Project Perplus!! 

このperplusは6か月間のjavaとフレイムワークとデータベースの教育で最終プロジェクトとして5人チームで進めたプロジェクトです。

perplusはairbnbの機能を基本としてそこに観光地のレビュ-掲示板を追加したサイトを作ることを目的としました。

私が担当したのは宿所(house)の登録とサーチ、削除、修正そしてgoogle maps apiを使ってdBに保存されている宿所の位置情報をもってマップ上に
マーカーで現れる事でした。その他のログインとか掲示板登録とかはすべて前に例題として授業で習ったのでもっとほかのAPIを使ってみたく思いその仕事を担当しました。（～house,map.js）

プロジェクトを進む中でdBに保存されている宿所の情報を画面のマップの大きさとか求めている日程とか部屋の数とか価格とかいろいろなリストのフィルターで濾してマーカーを出力するためにmybatisで提供するダイナミックSQLの基本文法を使ってcontrollerでフィルターの集まりをhashmapで送ればDBそのもので求めている情報だけを絞ってselect可能になれるようにしました。(housemapper.xml)

