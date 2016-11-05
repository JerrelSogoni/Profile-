import os
filename = "../rsrc/MockMessageData.txt"
def createInsert():
	with open(filename, "r") as f:
		for line in f:
			spl = line.split(':')

			print 'INSERT INTO Message VALUES(%s, %s, %s, %s, \'%s\', \'%s\');' % (spl[0], spl[1], spl[2], spl[3], spl[4], spl[5].strip())


createInsert()
