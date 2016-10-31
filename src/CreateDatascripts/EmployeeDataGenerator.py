import os
filename = "../rsrc/dataOct-31-2016.csv"
def createInsert():
	with open(filename, "r") as f:
		for line in f:
			spl = line.split(':')
			
			print 'INSERT INTO Employee VALUES("%s", "%s", "%s", "%s", "%s", "%s", "%s", "%s", \'%s\', %s);' % (spl[0], spl[1], spl[2], spl[3], spl[4], spl[5], spl[6], spl[7], spl[8], spl[9].strip())
		

createInsert()
