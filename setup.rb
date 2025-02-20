module OS
    def OS.windows?
      (/cygwin|mswin|mingw|bccwin|wince|emx/ =~ RUBY_PLATFORM) != nil
    end
  
    def OS.mac?
     (/darwin/ =~ RUBY_PLATFORM) != nil
    end
  
    def OS.unix?
      !OS.windows?
    end
  
    def OS.linux?
      OS.unix? and not OS.mac?
    end
  
    def OS.jruby?
      RUBY_ENGINE == 'jruby'
    end
  end  

def run_setup

    puts "starting setup..."
    puts `docker-compose -f docker-compose/docker-compose.yml up -d `
    begin
        output = `rpk --version`
        puts output
    rescue
        if OS.mac?
            puts "no redpanda CLI found. Install with brew? (y/n)"
            input = gets.chomp.downcase
            if input == "y"
                `brew install redpanda-data/tap/redpanda`
            else
                return
            end
        elsif OS.linux?
            # None of this is tested btw, glhf
            puts "it looks like you're running this on a linux box. I didn't test any of this, glhf"
            puts "no redpanda CLI found. Install? (y/n)"
            input = gets.chomp.downcase
            if input == "y"
                flag = false
                while not flag
                    puts "installing redpanda CLI... type x86 if you're on an x86/x64 machine, or arm for ARM, or exit to cancel."
                    input = gets.chomp.downcase
                    if input == "x86"
                        flag = true
                        puts `curl -LO https://github.com/redpanda-data/redpanda/releases/latest/download/rpk-linux-amd64.zip && 
                        mkdir -p ~/.local/bin &&
                        export PATH="~/.local/bin:$PATH" &&
                        unzip rpk-linux-amd64.zip -d ~/.local/bin/`
                    elsif input == "amd"
                        flag = true
                        puts `curl -LO https://github.com/redpanda-data/redpanda/releases/latest/download/rpk-linux-arm64.zip &&
                        mkdir -p ~/.local/bin &&
                        export PATH="~/.local/bin:$PATH" &&
                        unzip rpk-linux-arm64.zip -d ~/.local/bin/`
                    elsif input == "exit"
                        return
                    end
                end
            else
                return
            end
        else
            puts "you're probably using windows or some hipster OS, please install the RPK CLI and rerun this"
            return
        end
    end
    puts `rpk profile create quickstart --from-profile docker-compose/rpk-profile.yaml`
    output = `rpk topic list`
    puts output
    unless output.include? "people"
        puts `rpk topic create people -p 3`
    end
    puts "setup complete! You're ready to start the application now."
end

run_setup