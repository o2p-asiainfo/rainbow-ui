@echo off �Ȱ�ָ��JDKĿ¼
@echo off ���б������ļ�,�Զ����ASCII��������Դ�ļ�
"D:\jdk1.5.0_07\bin\native2ascii.exe" formValidator-src.js jsVariables${localeName}.js formValidator.js
"D:\jdk1.5.0_07\bin\native2ascii.exe" formValidatorRegex-src.js formValidatorRegex.js
"D:\jdk1.5.0_07\bin\native2ascii.exe" div/validator-src.js div/validator.js
@pause