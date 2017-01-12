TEX = texfot pdflatex -shell-escape -file-line-error
BIB = biber
NAME = report
TEXFILE = ${NAME}.tex
PDFFILE = ${NAME}.pdf
BCFFILE = ${NAME}.bcf

.PHONY: all clean clean_all view tex

all : ${PDFFILE} chapters/*.tex

${PDFFILE} : clean_all
	${TEX} ${NAME}
	${BIB} ${NAME}
	${TEX} ${NAME} 
	${TEX} ${NAME} 

clean : 
	@rm -f *.bbl
	@rm -f *.log
	@rm -f *.out
	@rm -f *.blg
	@rm -f *.toc
	@rm -f *.aux
	@rm -f *.bak
	@rm -f *.pyg
	@rm -f *.backup
	@rm -f *.lof
	@rm -f *.synctex.gz
	@rm -f *.run.xml
	@rm -f *.bcf
	@rm -f -rf _minted*
	@rm -f *.glsdefs
	@rm -f *.dot
	@rm -f *.ps
	@rm -f *.tr

clean_all : clean
	@rm -f *.pdf

view :
	okular ${PDFFILE} &

tex : ${TEXFILE} chapters/*.tex 
	${TEX} ${NAME}
