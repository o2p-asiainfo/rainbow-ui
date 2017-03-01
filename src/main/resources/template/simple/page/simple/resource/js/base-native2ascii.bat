@echo off 先把指定JDK目录
@echo off 运行本批处理文件,自动生成ASCII编码后的资源文件
"C:\jdk1.5.0\bin\native2ascii.exe" divcreat-src.js divcreat.js
@pause