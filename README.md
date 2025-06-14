# RecordingDiet - レコーディングダイエットWebアプリ

## 概要

RecordingDiet は、ユーザーごとの体重・BMIを記録・可視化するWebアプリです。  
日々の健康管理をサポートすることを目的とし、ログイン機能・データ登録・グラフ表示などの機能を備えています。
本アプリケーションは **MVCモデル（Model-View-Controller）** に基づいて構成されており、ロジックの分離・保守性の向上を意識して実装しています。

---
## 📁 プロジェクト構成

```
recordingDiet/
├── .git/
├── .settings/
├── build/
├── src/
│   └── main/
│       └── java/
│           ├── controller/
│           │   ├── DeleteServlet.java
│           │   ├── EditServlet.java
│           │   ├── InputServlet.java
│           │   ├── LoginServlet.java
│           │   ├── LogoutServlet.java
│           │   ├── RegisterServlet.java
│           │   └── ResultServlet.java
│           ├── dao/
│           │   ├── DbConnector.java
│           │   ├── DietDAO.java
│           │   └── UserDAO.java
│           ├── model/
│           │   ├── Log.java
│           │   └── User.java
│           └── util/
│               ├── PasswordUtil.java
│               └── SessionUtil.java
├── webapp/
│   ├── css/
│   │   ├── complete.css
│   │   ├── edit.css
│   │   ├── input.css
│   │   ├── login.css
│   │   ├── register.css
│   │   └── result.css
│   ├── images/
│   │   ├── himan.png
│   │   ├── himanF.png
│   │   ├── himanM.png
│   │   ├── nomal.png
│   │   ├── nomalF.png
│   │   ├── nomalM.png
│   │   ├── yase.png
│   │   ├── yaseF.png
│   │   └── yaseM.png
│   ├── META-INF/
│   │   ├── context.xml
│   │   └── MANIFEST.MF
│   └── WEB-INF/
│       ├── classes/
│       ├── lib/
│       ├── view/
│       │   ├── created_account.jsp
│       │   ├── edit.jsp
│       │   ├── input.jsp
│       │   ├── login.jsp
│       │   ├── register.jsp
│       │   └── result.jsp
│       └── web.xml
├── .classpath
├── .gitignore
├── .project
├── recordingDiet.war
└── README.md
```
---

## 使用技術

- Java（Servlet / JSP）
- HTML / CSS / JavaScript
- MariaDB（MySQL互換）
- Apache Tomcat
- さくらのVPS（デプロイ環境）

---

## 主な機能

- ユーザー登録 / ログイン（パスワードはソルト付きでハッシュ化）
- 身長・性別などの初期登録
- 日々の体重/BMIの記録・編集
- 記録一覧をテーブル形式で表示
- 最新のBMIに応じた画像表示
- 体重推移の折れ線グラフ表示（実装予定）

---

## データベース構成

### `users` テーブル

| カラム名    | 型       | 説明                       |
|-------------|----------|----------------------------|
| user_id     | INT      | 主キー、自動採番           |
| user_name   | VARCHAR  | ユーザー名                 |
| user_pass   | VARCHAR  | ハッシュ化されたパスワード |
| salt        | VARCHAR  | パスワード用ソルト         |
| height      | FLOAT    | 身長                       |
| gender      | VARCHAR  | 性別                       |

### `logs` テーブル（予定）

| カラム名    | 型       | 説明                               |
|-------------|----------|------------------------------------|
| log_id      | INT      | 主キー、自動採番                   |
| user_id     | INT      | 外部キー（users.user_id）         |
| weight      | FLOAT    | 体重                               |
| input_date  | DATE     | 入力日                             |

---

## 工夫したポイント

- BMIに応じて異なるイラストを表示（やせ型／標準／肥満など）
- MVC構成を意識し、処理ロジック（Servlet）と画面表示（JSP）を分離
- ログイン中ユーザーのみに表示データを制限
- 入力フォームのレイアウトやUIを調整し、実用性を意識
- result.jspでは表示専用ページとして機能を分離
- LGBTQ+の方への配慮として、性別のカラムは”どちらでもない”を選択可能
- 性別”どちらでもない”を選択した場合、動物のイラストを表示

---

## 今後の実装予定


- 入力済みデータの編集画面の改良
- 日付フィルターによる検索・分析機能
- エラーメッセージの明示化とUI改善

---

## テストユーザー（公開時用）

| ID     | Password |
|--------|----------|
| testA  | test     |
| testB  | test     |
| testC  | test     |

---

## 注意事項

- VPS上のTomcatで動作させているため、初回表示に時間がかかる場合があります。
- セキュリティ面ではSQLインジェクション対策など最低限の配慮を行っていますが、商用利用は想定しておりません。

---


