import os 
import string
import re
from contextlib import contextmanager
from requests.structures import CaseInsensitiveDict
import time

def main():
    file_names = [
        "Novel-Samples\\BİLİM İŞ BAŞINDA.txt",
        "Novel-Samples\\BOZKIRDA.txt", 
        "Novel-Samples\\DEĞİŞİM.txt", 
        "Novel-Samples\\DENEMELER.txt",
        "Novel-Samples\\UNUTULMUŞ DİYARLAR.txt",
    ]
    start_time = time_ms()
    allFiles = read_files(file_names=file_names)
    print("-----------------------------------------------------------------------------------------------------------------------")
    print("Removing punctuations and multiple spaces...")
    print("-----------------------------------------------------------------------------------------------------------------------")
    allFiles = allFiles.translate(str.maketrans('', '', string.punctuation))
    RE_MULTIPLE_SPACE = re.compile(r"( )+")
    allFiles = RE_MULTIPLE_SPACE.sub(" ", allFiles).strip()

    one_gram = analyze_ngram(n=1, file=allFiles)
    two_gram = analyze_ngram(n=2, file=allFiles)
    three_gram = analyze_ngram(n=3, file=allFiles)
    
    print_ngrams(one_gram=one_gram, two_gram=two_gram, three_gram=three_gram)
    print("-----------------------------------------------------------------------------------------------------------------------")
    print("\nExecution completed in", time_ms() - start_time, "milliseconds.", end="\n\n")
    input("Press Enter to continue...")

def read_files(file_names=[]):
    content = ""
    loc = os.path.dirname(os.path.abspath(__file__))
    start_time = time_ms()
    for file_name in file_names:
        file_name = os.path.join(loc, file_name)
        print("Reading ", file_name)
        with open_with_error(file_name) as (f, err):
            if err:
                print("IOError:", err)
            else:
                content += f.read().replace('\n', ' ').lower()
    print("\n", len(file_names), " files has been read in ", time_ms() - start_time, " milliseconds.", sep="")
    if len(content) == 0:
        print("\nThere is not any word to analyze!\nPLEASE PUT TXT FILES IN Novel-Samples FOLDER!!!!\n")
        input("Press Enter to continue...")
        exit()
    return content


def analyze_ngram(n=1, file=""):
    print("Generating ", n, "-gram .... ", sep="", end="")
    words = file.split(" ")
    n_grams = CaseInsensitiveDict()
    for i in range(n-1, len(words)):
        n_gram = ""
        for k in range(i, i-n, -1):
            n_gram = (words[k] + " " + n_gram).strip()
        if n_grams.get(n_gram) is not None:
            n_grams[n_gram] += 1
        else:
            n_grams[n_gram] = 1
    print("Sorting .... ", end="")
    start_time = time_ms()
    sorted_n_grams = sorted(n_grams.items(), key=lambda x: x[1], reverse=True)
    print("Completed in", time_ms() - start_time, "milliseconds.")
    return sorted_n_grams


def print_ngrams(one_gram=None, two_gram=None, three_gram=None):
    
    print("-----------------------------------------------------------------------------------------------------------------------")
    print("\n%6s   %-14s%14s       %-14s%14s       %-19s%19s" % (" ", "1-Gram", "Frequency", "2-Gram", "Frequency", "3-Gram", "Frequency"))
    print("-----------------------------------------------------------------------------------------------------------------------")
    for i in range(0, 100):
        print("%3d. |   %-23s %4d   |   %-23s %4d   |   %-33s %4d " % ( 
                i+1, 
                one_gram[i][0], 
                one_gram[i][1], 
                two_gram[i][0], 
                two_gram[i][1], 
                three_gram[i][0], 
                three_gram[i][1]
            ))


def time_ms(): 
    return int(time.time_ns() / 1000000)

@contextmanager
def open_with_error(filename, mode="r"):
    try: 
        f = open(filename, mode)
    except IOError as err:
        yield None, err
    else:
        try:
            yield f, None
        finally:
            f.close()


if __name__ == "__main__":
    main()