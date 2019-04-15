[
  {
    "rootLink": "http://api.zhuishushenqi.com",
    "sourceName": "追书神器",
    "sourceType": "json",
    "encodeType": "utf-8",
    "searchLink": "/book/fuzzy-search?query=%s",
    "ruleSearchBooks": "books",
    "ruleSearchTitle": "title",
    "ruleSearchAuthor": "author",
    "ruleSearchDesc": "shortIntro",
    "ruleSearchCover": "cover@http://statics.zhuishushenqi.com%s",
    "ruleSearchLink": "_id@http://api.zhuishushenqi.com/book/%s",
    "ruleCatalogLink": "_id@http://api.zhuishushenqi.com/mix-atoc/%s?view=chapters",
    "ruleFindLink": "_id@http://api.zhuishushenqi.com/book/%s/recommend",
    "ruleChapters": "mixToc$chapters",
    "ruleChapterLink": "link@UrlEncode#http://chapterup.zhuishushenqi.com/chapter/%s",
    "ruleChapterTitle": "title",
    "ruleChapterContent": "chapter$body",
    "orderNumber": "0",
    "isSelected": "true",
    "weight": "0"
  },
  {
    "rootLink": "https://www.xbiquge6.com",
    "sourceName": "新笔趣阁",
    "sourceType": "xpath",
    "encodeType": "utf-8",
    "searchLink": "/search.php?keyword=%s",
    "ruleSearchBooks": "//div[@class='result-game-item-detail']",
    "ruleSearchTitle": "//div[@class='result-game-item-detail']//h3//a/@title",
    "ruleSearchAuthor": "//div[@class='result-game-item-info']//p[1]/span[2]/text()",
    "ruleSearchDesc": "//p[@class='result-game-item-desc']/text()",
    "ruleSearchCover": "//div[@class='result-game-item-pic']//a//img/@src",
    "ruleSearchLink": "//div[@class='result-game-item-detail']//h3//a/@href",
    "ruleChapters": "//div[@id='list']",
    "ruleChapterLink": "//div[@id='list']//dl//dd//a/@href",
    "ruleChapterTitle": "//div[@id='list']//dl//dd//a/text()",
    "ruleChapterContent": "//div[@id='content']",
    "orderNumber": "0",
    "isSelected": "true",
    "weight": "0"
  },
  {
    "rootLink": "https://www.zwdu.com",
    "sourceName": "八一中文网",
    "sourceType": "xpath",
    "encodeType": "gbk&utf-8",
    "searchLink": "/search.php?keyword=%s",
    "ruleSearchBooks": "//div[@class='result-game-item-detail']",
    "ruleSearchTitle": "//div[@class='result-game-item-detail']//h3//a/@title",
    "ruleSearchAuthor": "//div[@class='result-game-item-info']//p[1]/span[2]/text()",
    "ruleSearchDesc": "//p[@class='result-game-item-desc']/text()",
    "ruleSearchCover": "//div[@class='result-game-item-pic']//a//img/@src",
    "ruleSearchLink": "//div[@class='result-game-item-detail']//h3//a/@href",
    "ruleChapters": "//div[@id='list']",
    "ruleChapterLink": "//div[@id='list']//dl//dd//a/@href",
    "ruleChapterTitle": "//div[@id='list']//dl//dd//a/text()",
    "ruleChapterContent": "//div[@id='content']",
    "orderNumber": "0",
    "isSelected": "true",
    "weight": "0"
  },
  {
    "rootLink": "https://www.liewen.cc",
    "sourceName": "猎文",
    "sourceType": "xpath",
    "encodeType": "gbk&utf-8",
    "searchLink": "/search.php?keyword=%s",
    "ruleSearchBooks": "//div[@class='result-game-item-detail']",
    "ruleSearchTitle": "//div[@class='result-game-item-detail']//h3//a/@title",
    "ruleSearchAuthor": "//div[@class='result-game-item-info']//p[1]/span[2]/text()",
    "ruleSearchDesc": "//p[@class='result-game-item-desc']/text()",
    "ruleSearchCover": "//div[@class='result-game-item-pic']//a//img/@src",
    "ruleSearchLink": "//div[@class='result-game-item-detail']//h3//a/@href",
    "ruleChapters": "//div[@id='list']",
    "ruleChapterLink": "//div[@id='list']//dl//dd//a/@href",
    "ruleChapterTitle": "//div[@id='list']//dl//dd//a/text()",
    "ruleChapterContent": "//div[@id='content']",
    "orderNumber": "0",
    "isSelected": "true",
    "weight": "0"
  },
  {
    "rootLink": "https://www.xszww.com",
    "sourceName": "小说中文",
    "sourceType": "xpath",
    "encodeType": "gbk",
    "searchLink": "/s.php?ie=gbk&s=10385337132858012269&q=%s",
    "ruleSearchBooks": "//div[@class='bookinfo']",
    "ruleSearchTitle": "//div[@class='bookinfo']//h4//a/text()",
    "ruleSearchAuthor": "//div[@class='author']/text()",
    "ruleSearchDesc": "//div[@class='bookinfo']//p/text()",
    "ruleSearchCover": "//div[@class='bookimg']//a//img/@src",
    "ruleSearchLink": "//div[@class='bookimg']//a/@href",
    "ruleChapters": "//div[@class='listmain']",
    "ruleChapterLink": "//div[@class='listmain']/dl/dt[2]/following-sibling::dd/a/@href",
    "ruleChapterTitle": "//div[@class='listmain']/dl/dt[2]/following-sibling::dd/a/text()",
    "ruleChapterContent": "//div[@id='content']",
    "orderNumber": "0",
    "isSelected": "true",
    "weight": "0"
  },
  {
    "rootLink": "https://www.bimo.cc",
    "sourceName": "追书",
    "sourceType": "xpath",
    "encodeType": "utf-8",
    "searchLink": "/search.aspx?keyword=%s",
    "ruleSearchBooks": "//div[@class='result-game-item-detail']",
    "ruleSearchTitle": "//div[@class='result-game-item-detail']//h3//a/@title",
    "ruleSearchAuthor": "//div[@class='result-game-item-info']//p[1]/span[2]/text()",
    "ruleSearchDesc": "//p[@class='result-game-item-desc']/text()",
    "ruleSearchCover": "//div[@class='result-game-item-pic']//a//img/@src",
    "ruleSearchLink": "//div[@class='result-game-item-detail']//h3//a/@href",
    "ruleChapters": "//div[@id='list']",
    "ruleChapterLink": "//div[@id='list']//dl//dd//a/@href",
    "ruleChapterTitle": "//div[@id='list']//dl//dd//a/text()",
    "ruleChapterContent": "//div[@id='content']",
    "orderNumber": "0",
    "isSelected": "true",
    "weight": "0"
  }
  
]