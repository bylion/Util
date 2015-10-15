# Python 2 code
import urllib2
import os


with open ("url.list", "r") as myfile:
    url_list=myfile.read().splitlines()

print url_list

directory = "resource"

if not os.path.exists(directory):
    os.makedirs(directory)

for url in url_list:
	print "downloading with " + url
	f = urllib2.urlopen(url)
	data = f.read()
	filename = url[url.rfind('/'):]
	with open("resource/"+filename, "wb") as code:
		code.write(data)
 