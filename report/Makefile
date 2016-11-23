TEX = texfot pdflatex -shell-escape -file-line-error
BIB = biber
NAME = report
TEXFILE = ${NAME}.tex
PDFFILE = ${NAME}.pdf
BCFFILE = ${NAME}.bcf

.PHONY: all clean clean_all view tex

all : ${PDFFILE}

${PDFFILE} : clean_all
	${TEX} ${NAME}
	${BIB} ${NAME}
	${TEX} ${NAME} 
	${TEX} ${NAME} 

clean : 
	@rm *.bbl || true
	@rm *.log || true
	@rm *.out || true
	@rm *.blg || true
	@rm *.toc || true
	@rm *.aux || true
	@rm *.bak || true
	@rm *.pyg || true
	@rm *.backup || true
	@rm *.lof || true
	@rm *.synctex.gz || true
	@rm *.run.xml || true
	@rm *.bcf || true
	@rm -rf _minted* || true
	@rm *.glsdefs || true
	@rm *.dot || true
	@rm *.ps || true

clean_all : clean
	@rm ${PDFFILE} || true 
	@rm *.pdf || true

view :
	okular ${PDFFILE} &

tex : ${TEXFILE} chapters/*.tex 
	${TEX} ${NAME}
