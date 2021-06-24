/*
存放homePage的全局变量
 */
let homePage_$b = $("#littlePictB").find("a").find("img");
let homePage_$a = $("#littlePictA").find("a").not($("#bigpict").find("a")).find("img");
let homePage_littleSize=homePage_$a.length+homePage_$b.length;
let homePage_$big=$("#bigpict").find("li").find("img");
let homePage_bigSize=homePage_$big.length;
let homePage_sum=homePage_bigSize+homePage_littleSize;

let homePage_$ul = $("ul.movie-schedule");
let homePage_typeArray=new Array();
let homePage_sizeArray=new Array();

