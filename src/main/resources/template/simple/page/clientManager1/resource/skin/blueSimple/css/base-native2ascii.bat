@echo off 先把指定JDK目录
@echo off 运行本批处理文件,自动生成ASCII编码后的资源文件
"D:\jdk1.5.0_07\bin\native2ascii.exe" css-src.css css.css
@pause