
#Made by Jerrel Sogoni, Creates Userdata in our database
with open('../rsrc/MockUserData.txt') as f:
	lines = f.read().splitlines()
count = 0
f = open('mockUsers.sql','w')
for line in lines:
	line.strip()
	if(count == 0):
		f.write( "INSERT INTO UserPlus VALUES(" + line + ", ")
		count += 1
	elif(count ==1):
		f.write( "'" + line + "', ")
		count +=1
	elif(count == 7):
		f.write( line + ", ")
		count += 1
	elif(count == 10):
		f.write( line + ", ")
		count += 1
	elif(count == 11):
		f.write(line + ", ")
		count += 1
	elif(count == 13):
		f.write( "'" + line + "'" + ");\n")
		count = 0
	else:
		f.write( "'" + line + "'" + ", ")
		count += 1


