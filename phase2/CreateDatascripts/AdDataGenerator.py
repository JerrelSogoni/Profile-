import os
filename = "../rsrc/AdData.csv"
def createInsert():
	with open(filename, "r") as f:
		for line in f:
			spl = line.split(':')
			
			print 'INSERT INTO AdData VALUES(%s, "%s", "%s", "%s", "%s", "%s", %s, %s);' % (spl[0], spl[1], spl[2], spl[3], spl[4], spl[5], spl[6], spl[7].strip())
		

createInsert()
