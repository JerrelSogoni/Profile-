import os
filename = "../rsrc/MockGroupPage.txt"
def createInsert():
	with open(filename, "r") as f:
		for line in f:
			spl = line.split(':')

			print 'INSERT INTO GroupPage VALUES(%s, %s);' % (spl[0], spl[1].strip())


createInsert()
