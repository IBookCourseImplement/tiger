java = java
outdir = bin
arg = cp
mainclass = Tiger
baseCompiler = $(java) -$(arg) $(outdir) $(mainclass)

help:
	$(baseCompiler) -help

testall:
	cd test && sh ./test.sh