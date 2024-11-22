<html>
<head>
    <Script Language="JavaScript">
        function add()
        {
            document.calcForm.action="/jee/add.html";
            document.calcForm.method="get"
            document.calcForm.submit();
        }

        function subtract()
        {
            document.calcForm.action="/jee/subtract.html";
            document.calcForm.submit();
        }

        function multiply()
        {
            document.calcForm.action="/jee/multiply.html";
            document.calcForm.submit();
        }
        function div()
        {
            document.calcForm.action="/jee/div.html";
            document.calcForm.submit();
        }

    </Script>
</head>
<body>
<h2>Calculator</h2>
<form name = "calcForm" action="/jee/formResults.html" method="get">
    <input type="number" name="first"/>
    <input type="number" name="second"/>
    <br>
    <input type="submit" value="add" onClick="add()">
    <input type="submit" value="subtract" onClick="subtract()">
    <input type="submit" value="multiply" onClick="multiply()">
    <input type="submit" value="div" onClick="div()">
</form>
</body>
</html>
