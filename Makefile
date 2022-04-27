java = java
outdir = bin
arg = cp
mainclass = Tiger
baseCompiler = $(java) -$(arg) $(outdir) $(mainclass)

help:
	$(baseCompiler) -help

slpargs:
	$(baseCompiler) -slp args

slpinterp:
	$(baseCompiler) -slp interp

testlexer:
	$(baseCompiler) ./test/Fac -testlexer

testall:
	cd test && sh ./test.sh