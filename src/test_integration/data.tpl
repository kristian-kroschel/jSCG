$' testtemplate with data
$' the following lines define some data:
$' first the data 
$!
  csvdata(testnode,node);
  csvdata(testnode,child,achildnode);
  csvdata(testnode,attribute,atribname,atribValueTestNode);
  csvdata(anothernode,node);
  csvdata(anothernode,attribute,atribname,atribValueAnotherNode);
  csvdata(achildnode,node);
  csvdata(achildnode,attribute,atribname,atribvalue);
!$

Output:

$> for (var i=0; i<JCSG_DATANODE.childcount; i++) { $> 
Line <$ print(i); <$
$> 
	print('childs:');
	print(JCSG_ROOTNODE.getChild(i).name); 
<$

a backslash escapes also the scripting tags: \<$ print('no script') \$>
$> } <$


